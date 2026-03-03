package co.anbora.labs.nushell.community.lang;

import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;
import java.util.Stack;

import static com.intellij.psi.TokenType.BAD_CHARACTER;
import static com.intellij.psi.TokenType.WHITE_SPACE;
import static co.anbora.labs.nushell.community.lang.core.psi.NuShellTypes.*;

%%

%{
    private Stack<Integer> stateStack = new Stack<>();
    private int interpParenDepth = 0;
    private int rawHashCount = 0;
    private boolean atLineStart = true;
    private String heredocTag = null;

    private void pushState(int state) {
        stateStack.push(yystate());
        yybegin(state);
    }

    private void popState() {
        if (!stateStack.isEmpty()) {
            yybegin(stateStack.pop());
        } else {
            yybegin(YYINITIAL);
        }
    }
%}

%{
  public NuShellLexer() {
    this(null);
  }
%}

%public
%class NuShellLexer
%implements FlexLexer
%function advance
%type IElementType
%unicode

%s STRING_DOUBLE
%s STRING_INTERP
%s STRING_INTERP_SQ
%s INTERP_EXPR
%s RAW_STRING
%s STRING_TRIPLE
%s STRING_TRIPLE_INTERP
%s LINE_CONT
%s DOC_ATTR_STATE
%s HEREDOC_BODY

///////////////////////////////////////////////////////////////////////////////////////////////////
// Whitespace
///////////////////////////////////////////////////////////////////////////////////////////////////
EOL_WS           = \n | \r | \r\n
LINE_WS          = [\ \t\f]
WHITE_SPACE      = {LINE_WS}+

///////////////////////////////////////////////////////////////////////////////////////////////////
// Comments
///////////////////////////////////////////////////////////////////////////////////////////////////
LINE_COMMENT     = "#" [^\r\n]*
SHEBANG          = "#!" [^\r\n]*

///////////////////////////////////////////////////////////////////////////////////////////////////
// Numbers
///////////////////////////////////////////////////////////////////////////////////////////////////
DIGIT            = [0-9]
DIGIT_US         = [0-9_]
HEX_LITERAL      = 0[xX][0-9a-fA-F][0-9a-fA-F_]*
OCT_LITERAL      = 0[oO][0-7][0-7_]*
BIN_LITERAL      = 0[bB][01][01_]*

// Duration suffixes
DURATION_SUFFIX  = (ns|us|\u00B5s|ms|sec|min|hr|day|wk)

// Filesize suffixes (SI then IEC, case variations)
FILESIZE_SUFFIX  = (b|B|kb|kB|Kb|KB|mb|mB|Mb|MB|gb|gB|Gb|GB|tb|tB|Tb|TB|pb|pB|Pb|PB|eb|eB|Eb|EB|kib|KiB|Kib|kiB|mib|MiB|Mib|miB|gib|GiB|Gib|giB|tib|TiB|Tib|tiB|pib|PiB|Pib|piB|eib|EiB|Eib|eiB)

///////////////////////////////////////////////////////////////////////////////////////////////////
// Strings
///////////////////////////////////////////////////////////////////////////////////////////////////
SINGLE_STRING    = '[^']*'

///////////////////////////////////////////////////////////////////////////////////////////////////
// Raw strings: r#"..."# with variable # count
///////////////////////////////////////////////////////////////////////////////////////////////////

///////////////////////////////////////////////////////////////////////////////////////////////////
// Variables
///////////////////////////////////////////////////////////////////////////////////////////////////
VAR_NAME         = [a-zA-Z_][a-zA-Z0-9_\-]*
VARIABLE         = "$" {VAR_NAME}
VAR_POSITIONAL   = "$" {DIGIT}+

///////////////////////////////////////////////////////////////////////////////////////////////////
// Flags
///////////////////////////////////////////////////////////////////////////////////////////////////
LONG_FLAG        = "--" [a-zA-Z][a-zA-Z0-9\-]*
SHORT_FLAG       = "-" [a-zA-Z]

///////////////////////////////////////////////////////////////////////////////////////////////////
// Identifiers (including Unicode)
///////////////////////////////////////////////////////////////////////////////////////////////////
IDENTIFIER       = [a-zA-Z_\u0080-\uFFFF][a-zA-Z0-9_\-\u0080-\uFFFF]*

///////////////////////////////////////////////////////////////////////////////////////////////////
// Glob patterns
///////////////////////////////////////////////////////////////////////////////////////////////////
GLOB_PATTERN     = [a-zA-Z0-9_./\-]*[*?][a-zA-Z0-9_./\-*?]*

///////////////////////////////////////////////////////////////////////////////////////////////////
// Typed literals
///////////////////////////////////////////////////////////////////////////////////////////////////
TYPED_INT_SUFFIX   = (i8|i16|i32|i64|u8|u16|u32|u64)
TYPED_FLOAT_SUFFIX = (f32|f64)

%%

// ─── Line continuation ─────────────────────────────────────────────────────────
<YYINITIAL, INTERP_EXPR> {
    "\\" {EOL_WS}          { atLineStart = true; return LINE_CONTINUATION; }
}

// ─── Whitespace & newlines (all states) ────────────────────────────────────────
<YYINITIAL, INTERP_EXPR> {
    {EOL_WS}+              { atLineStart = true; return LINE_TERMINATOR; }
    {WHITE_SPACE}          { return WHITE_SPACE; }
}

// ─── Shebang (only at very start of file) ──────────────────────────────────────
<YYINITIAL> {
    {SHEBANG}              { if (atLineStart && getTokenStart() == 0) { atLineStart = false; return SHEBANG_COMMENT; } else { atLineStart = false; return EOL_COMMENT; } }
}

// ─── Doc attributes with dedicated state ───────────────────────────────────────
<YYINITIAL, INTERP_EXPR> {
    "#[" [^\]\r\n]* "]"    { atLineStart = false; return DOC_ATTRIBUTE; }
}

// ─── Comments ──────────────────────────────────────────────────────────────────
<YYINITIAL, INTERP_EXPR> {
    {LINE_COMMENT}         { atLineStart = false; return EOL_COMMENT; }
}

// ─── Compound keywords (longest match first) ──────────────────────────────────
<YYINITIAL, INTERP_EXPR> {
    "export-env"           { atLineStart = false; return EXPORT_ENV; }
    "source-env"           { atLineStart = false; return SOURCE_ENV; }
    "hide-env"             { atLineStart = false; return HIDE_ENV; }
    "not-in"               { atLineStart = false; return NOT_IN; }

    // Redirection: append combined (longest first)
    "out+err>>"            { atLineStart = false; return REDIRECT_APPEND_COMBINED; }
    "o+e>>"                { atLineStart = false; return REDIRECT_APPEND_COMBINED; }
    // Redirection: append
    "out>>"                { atLineStart = false; return REDIRECT_APPEND_OUT; }
    "err>>"                { atLineStart = false; return REDIRECT_APPEND_ERR; }
    "e>>"                  { atLineStart = false; return REDIRECT_APPEND_ERR; }
    // Pipeline: combined
    "out+err>|"            { atLineStart = false; return OUT_ERR_PIPE; }
    "o+e>|"                { atLineStart = false; return OUT_ERR_PIPE; }
    // Pipeline: error
    "e>|"                  { atLineStart = false; return ERR_PIPE; }
    "err>|"                { atLineStart = false; return ERR_PIPE; }
    // Redirection: combined
    "out+err>"             { atLineStart = false; return REDIRECT_COMBINED; }
    "o+e>"                 { atLineStart = false; return REDIRECT_COMBINED; }
    // Redirection: single
    "out>"                 { atLineStart = false; return REDIRECT_OUT; }
    "err>"                 { atLineStart = false; return REDIRECT_ERR; }
    "e>"                   { atLineStart = false; return REDIRECT_ERR; }
}

// ─── Keywords ──────────────────────────────────────────────────────────────────
<YYINITIAL, INTERP_EXPR> {
    // Definition keywords
    "def"                  { atLineStart = false; return DEF; }
    "let"                  { atLineStart = false; return LET; }
    "mut"                  { atLineStart = false; return MUT; }
    "const"                { atLineStart = false; return CONST; }
    "alias"                { atLineStart = false; return ALIAS; }
    "extern"               { atLineStart = false; return EXTERN; }

    // Control flow
    "if"                   { atLineStart = false; return IF; }
    "else"                 { atLineStart = false; return ELSE; }
    "match"                { atLineStart = false; return MATCH; }
    "for"                  { atLineStart = false; return FOR; }
    "while"                { atLineStart = false; return WHILE; }
    "loop"                 { atLineStart = false; return LOOP; }
    "break"                { atLineStart = false; return BREAK; }
    "continue"             { atLineStart = false; return CONTINUE; }
    "return"               { atLineStart = false; return RETURN; }

    // Error handling
    "try"                  { atLineStart = false; return TRY; }
    "catch"                { atLineStart = false; return CATCH; }

    // Modules
    "module"               { atLineStart = false; return MODULE; }
    "use"                  { atLineStart = false; return USE; }
    "export"               { atLineStart = false; return EXPORT; }
    "hide"                 { atLineStart = false; return HIDE; }
    "overlay"              { atLineStart = false; return OVERLAY; }
    "source"               { atLineStart = false; return SOURCE; }
    "as"                   { atLineStart = false; return AS; }

    // Other keywords
    "do"                   { atLineStart = false; return DO; }
    "where"                { atLineStart = false; return WHERE_KW; }
    "each"                 { atLineStart = false; return EACH_KW; }
    "collect"              { atLineStart = false; return COLLECT_KW; }
    "error"                { atLineStart = false; return ERROR_KW; }

    // Boolean & null
    "true"                 { atLineStart = false; return BOOL_LITERAL; }
    "false"                { atLineStart = false; return BOOL_LITERAL; }
    "null"                 { atLineStart = false; return NULL; }

    // Operator words
    "mod"                  { atLineStart = false; return MOD; }
    "in"                   { atLineStart = false; return IN; }
    "not"                  { atLineStart = false; return NOT; }
    "and"                  { atLineStart = false; return AND; }
    "or"                   { atLineStart = false; return OR; }
    "xor"                  { atLineStart = false; return XOR; }
    "bit-or"               { atLineStart = false; return BIT_OR; }
    "bit-and"              { atLineStart = false; return BIT_AND; }
    "bit-xor"              { atLineStart = false; return BIT_XOR; }
    "bit-shl"              { atLineStart = false; return BIT_SHL; }
    "bit-shr"              { atLineStart = false; return BIT_SHR; }
    "bit-not"              { atLineStart = false; return BIT_NOT; }
    "bit-rol"              { atLineStart = false; return BIT_ROL; }
    "bit-ror"              { atLineStart = false; return BIT_ROR; }
    "starts-with"          { atLineStart = false; return STARTS_WITH; }
    "ends-with"            { atLineStart = false; return ENDS_WITH; }

    // Special number words
    "inf"                  { atLineStart = false; return INF_LITERAL; }
    "-inf"                 { atLineStart = false; return INF_LITERAL; }
    "NaN"                  { atLineStart = false; return NAN_LITERAL; }
}

// ─── Multi-char operators (longest first) ──────────────────────────────────────
<YYINITIAL, INTERP_EXPR> {
    // Assignment compound
    "**="                  { atLineStart = false; return POWER_ASSIGN; }
    "//="                  { atLineStart = false; return DOUBLE_SLASH_ASSIGN; }
    "+="                   { atLineStart = false; return PLUS_ASSIGN; }
    "-="                   { atLineStart = false; return MINUS_ASSIGN; }
    "*="                   { atLineStart = false; return STAR_ASSIGN; }
    "/="                   { atLineStart = false; return SLASH_ASSIGN; }
    "%="                   { atLineStart = false; return PERCENT_ASSIGN; }

    // Arithmetic multi-char
    "**"                   { atLineStart = false; return POWER; }
    "//"                   { atLineStart = false; return DOUBLE_SLASH; }
    "++"                   { atLineStart = false; return PLUS_PLUS; }
    "--"                   { atLineStart = false; return MINUS_MINUS; }

    // Comparison
    "=="                   { atLineStart = false; return EQ; }
    "!="                   { atLineStart = false; return NEQ; }
    "<="                   { atLineStart = false; return LTE; }
    ">="                   { atLineStart = false; return GTE; }
    "=~"                   { atLineStart = false; return REGEX_MATCH; }
    "!~"                   { atLineStart = false; return REGEX_NOT_MATCH; }

    // Logical symbols
    "&&"                   { atLineStart = false; return AND_AND; }
    "||"                   { atLineStart = false; return OR_OR; }

    // Range operators
    "..."                  { atLineStart = false; return DOTDOTDOT; }
    "..="                  { atLineStart = false; return DOTDOT_EQ; }
    "..<"                  { atLineStart = false; return DOTDOT_LT; }
    ".."                   { atLineStart = false; return DOTDOT; }

    // Arrow operators
    "->"                   { atLineStart = false; return ARROW; }
    "=>"                   { atLineStart = false; return FAT_ARROW; }

    // Optional chaining
    "?."                   { atLineStart = false; return QUESTION_DOT; }

    // Double colon
    "::"                   { atLineStart = false; return DOUBLE_COLON; }

    // Tee pipe
    "|>"                   { atLineStart = false; return TEE_PIPE; }
}

// ─── Single-char operators ─────────────────────────────────────────────────────
<YYINITIAL, INTERP_EXPR> {
    "+"                    { atLineStart = false; return PLUS; }
    "-"                    { atLineStart = false; return MINUS; }
    "*"                    { atLineStart = false; return STAR; }
    "/"                    { atLineStart = false; return SLASH; }
    "%"                    { atLineStart = false; return PERCENT; }
    "="                    { atLineStart = false; return ASSIGN; }
    "<"                    { atLineStart = false; return LT; }
    ">"                    { atLineStart = false; return GT; }
    "!"                    { atLineStart = false; return EXCL; }
    "|"                    { atLineStart = false; return PIPE; }
    "&"                    { atLineStart = false; return AMPERSAND; }
}

// ─── Interpolated strings ──────────────────────────────────────────────────────
<YYINITIAL, INTERP_EXPR> {
    "$\"\"\""              { atLineStart = false; pushState(STRING_TRIPLE_INTERP); return INTERP_TRIPLE_STRING_START; }
    "$\""                  { atLineStart = false; pushState(STRING_INTERP); return INTERP_STRING_START; }
    "$'"                  { atLineStart = false; pushState(STRING_INTERP_SQ); return INTERP_STRING_START; }
}

// ─── Raw strings: r#"..."#  r##"..."##  r###"..."### ───────────────────────────
<YYINITIAL, INTERP_EXPR> {
    "r###\""               { atLineStart = false; rawHashCount = 3; pushState(RAW_STRING); return RAW_STRING_LITERAL; }
    "r##\""                { atLineStart = false; rawHashCount = 2; pushState(RAW_STRING); return RAW_STRING_LITERAL; }
    "r#\""                 { atLineStart = false; rawHashCount = 1; pushState(RAW_STRING); return RAW_STRING_LITERAL; }
}

<RAW_STRING> {
    "\"#"                  { if (rawHashCount == 1) { popState(); return RAW_STRING_LITERAL; } }
    "\"##"                 { if (rawHashCount == 2) { popState(); return RAW_STRING_LITERAL; } }
    "\"###"                { if (rawHashCount == 3) { popState(); return RAW_STRING_LITERAL; } }
    [^]                    { return RAW_STRING_LITERAL; }
}

// ─── String interpolation states ───────────────────────────────────────────────
<STRING_INTERP> {
    "("                    { interpParenDepth = 1; pushState(INTERP_EXPR); return INTERP_EXPR_START; }
    "\""                   { popState(); return INTERP_STRING_END; }
    "\\\\"                 { return INTERP_STRING_CONTENT; }
    "\\\""                 { return INTERP_STRING_CONTENT; }
    "\\("                  { return INTERP_STRING_CONTENT; }
    "\\n"                  { return INTERP_STRING_CONTENT; }
    "\\t"                  { return INTERP_STRING_CONTENT; }
    "\\r"                  { return INTERP_STRING_CONTENT; }
    "\\0"                  { return INTERP_STRING_CONTENT; }
    "\\u{" [0-9a-fA-F]+ "}" { return INTERP_STRING_CONTENT; }
    "\\x" [0-9a-fA-F]{2}  { return INTERP_STRING_CONTENT; }
    [^\"\\\(]+             { return INTERP_STRING_CONTENT; }
    [^]                    { return INTERP_STRING_CONTENT; }
}

<STRING_INTERP_SQ> {
    "("                    { interpParenDepth = 1; pushState(INTERP_EXPR); return INTERP_EXPR_START; }
    "'"                   { popState(); return INTERP_STRING_END; }
    "\\("                  { return INTERP_STRING_CONTENT; }
    [^'\(]+               { return INTERP_STRING_CONTENT; }
    [^]                    { return INTERP_STRING_CONTENT; }
}

// ─── Triple string interpolation state ─────────────────────────────────────────
<STRING_TRIPLE_INTERP> {
    "("                    { interpParenDepth = 1; pushState(INTERP_EXPR); return INTERP_EXPR_START; }
    "\"\"\""               { popState(); return INTERP_STRING_END; }
    "\\\\"                 { return INTERP_STRING_CONTENT; }
    "\\\""                 { return INTERP_STRING_CONTENT; }
    "\\("                  { return INTERP_STRING_CONTENT; }
    "\\n"                  { return INTERP_STRING_CONTENT; }
    "\\t"                  { return INTERP_STRING_CONTENT; }
    "\\r"                  { return INTERP_STRING_CONTENT; }
    "\\0"                  { return INTERP_STRING_CONTENT; }
    "\\u{" [0-9a-fA-F]+ "}" { return INTERP_STRING_CONTENT; }
    "\\x" [0-9a-fA-F]{2}  { return INTERP_STRING_CONTENT; }
    [^\"\\\(]+             { return INTERP_STRING_CONTENT; }
    [^]                    { return INTERP_STRING_CONTENT; }
}

<INTERP_EXPR> {
    "("                    { interpParenDepth++; return L_PAREN; }
    ")"                    {
                             interpParenDepth--;
                             if (interpParenDepth <= 0) {
                                 popState();
                                 return INTERP_EXPR_END;
                             }
                             return R_PAREN;
                           }
}

// ─── Triple-quoted strings (with dedicated state) ──────────────────────────────
<YYINITIAL, INTERP_EXPR> {
    "\"\"\""               { atLineStart = false; pushState(STRING_TRIPLE); return TRIPLE_STRING_LITERAL; }
}

<STRING_TRIPLE> {
    "\"\"\""               { popState(); return TRIPLE_STRING_LITERAL; }
    "\\\""                 { return TRIPLE_STRING_LITERAL; }
    [^\"]+ | \"            { return TRIPLE_STRING_LITERAL; }
}

// ─── Double-quoted strings (with dedicated state) ──────────────────────────────
<YYINITIAL, INTERP_EXPR> {
    "\""                   { atLineStart = false; pushState(STRING_DOUBLE); return STRING_LITERAL; }
}

<STRING_DOUBLE> {
    "\""                   { popState(); return STRING_LITERAL; }
    "\\\\"                 { return STRING_LITERAL; }
    "\\\""                 { return STRING_LITERAL; }
    "\\n"                  { return STRING_LITERAL; }
    "\\t"                  { return STRING_LITERAL; }
    "\\r"                  { return STRING_LITERAL; }
    "\\0"                  { return STRING_LITERAL; }
    "\\u{" [0-9a-fA-F]+ "}" { return STRING_LITERAL; }
    "\\x" [0-9a-fA-F]{2}  { return STRING_LITERAL; }
    "\\" .                 { return STRING_LITERAL; }
    [^\"\\]+               { return STRING_LITERAL; }
}

// ─── Backtick strings / command names ──────────────────────────────────────────
<YYINITIAL, INTERP_EXPR> {
    "`" [^`]* "`"          { atLineStart = false; return STRING_LITERAL; }
}

// ─── Regular single-quoted strings ─────────────────────────────────────────────
<YYINITIAL, INTERP_EXPR> {
    {SINGLE_STRING}        { atLineStart = false; return STRING_LITERAL; }
}

// ─── Format strings: date#"...", glob#"...", regex#"..." ───────────────────────
<YYINITIAL, INTERP_EXPR> {
    [a-zA-Z]+ "#\"" ([^\"\\] | "\\.")* "\""  { atLineStart = false; return FORMAT_STRING_LITERAL; }
}

// ─── Here-doc: <<EOF ... EOF ───────────────────────────────────────────────────
<YYINITIAL> {
    "<<" [a-zA-Z_][a-zA-Z0-9_]* { atLineStart = false; return HEREDOC_START; }
}

// ─── Durations (before numbers, number+suffix) ─────────────────────────────────
<YYINITIAL, INTERP_EXPR> {
    "-"? {DIGIT} {DIGIT_US}* {DURATION_SUFFIX}  { atLineStart = false; return DURATION_LITERAL; }
}

// ─── Filesizes (before numbers, number+suffix) ─────────────────────────────────
<YYINITIAL, INTERP_EXPR> {
    "-"? {DIGIT} {DIGIT_US}* {FILESIZE_SUFFIX}  { atLineStart = false; return FILESIZE_LITERAL; }
}

// ─── Numbers (special bases first) ─────────────────────────────────────────────
<YYINITIAL, INTERP_EXPR> {
    {HEX_LITERAL}          { atLineStart = false; return HEX_LITERAL; }
    {OCT_LITERAL}          { atLineStart = false; return OCT_LITERAL; }
    {BIN_LITERAL}          { atLineStart = false; return BIN_LITERAL; }

    // Typed float literals (before scientific/float)
    "-"? {DIGIT} {DIGIT_US}* "." {DIGIT} {DIGIT_US}* {TYPED_FLOAT_SUFFIX}  { atLineStart = false; return TYPED_FLOAT_LITERAL; }

    // Scientific notation (before float)
    "-"? {DIGIT} {DIGIT_US}* "." {DIGIT} {DIGIT_US}* [eE] [+-]? {DIGIT} {DIGIT_US}*  { atLineStart = false; return SCIENTIFIC_LITERAL; }
    "-"? {DIGIT} {DIGIT_US}* [eE] [+-]? {DIGIT} {DIGIT_US}*                           { atLineStart = false; return SCIENTIFIC_LITERAL; }

    // Float
    "-"? {DIGIT} {DIGIT_US}* "." {DIGIT} {DIGIT_US}*  { atLineStart = false; return FLOAT_LITERAL; }

    // Typed integer literals (before plain integer)
    {DIGIT} {DIGIT_US}* {TYPED_INT_SUFFIX}  { atLineStart = false; return TYPED_INT_LITERAL; }

    // Integer
    {DIGIT} {DIGIT_US}*   { atLineStart = false; return INT_LITERAL; }
}

// ─── Flags ─────────────────────────────────────────────────────────────────────
<YYINITIAL, INTERP_EXPR> {
    {LONG_FLAG}            { atLineStart = false; return LONG_FLAG; }
    {SHORT_FLAG}           { atLineStart = false; return SHORT_FLAG; }
}

// ─── Special variables ─────────────────────────────────────────────────────────
<YYINITIAL, INTERP_EXPR> {
    "$env"                 { atLineStart = false; return VAR_ENV; }
    "$nu"                  { atLineStart = false; return VAR_NU; }
    "$it"                  { atLineStart = false; return VAR_IT; }
    "$in"                  { atLineStart = false; return VAR_IN; }
    "$nothing"             { atLineStart = false; return VAR_NOTHING; }
}

// ─── Variables ─────────────────────────────────────────────────────────────────
<YYINITIAL, INTERP_EXPR> {
    {VAR_POSITIONAL}       { atLineStart = false; return VARIABLE; }
    {VARIABLE}             { atLineStart = false; return VARIABLE; }
    "$"                    { atLineStart = false; return DOLLAR; }
}

// ─── Delimiters ────────────────────────────────────────────────────────────────
<YYINITIAL, INTERP_EXPR> {
    "{"                    { atLineStart = false; return L_BRACE; }
    "}"                    { atLineStart = false; return R_BRACE; }
    "["                    { atLineStart = false; return L_BRACK; }
    "]"                    { atLineStart = false; return R_BRACK; }
    "("                    { atLineStart = false; return L_PAREN; }
    ")"                    { atLineStart = false; return R_PAREN; }
    ":"                    { atLineStart = false; return COLON; }
    ","                    { atLineStart = false; return COMMA; }
    "."                    { atLineStart = false; return DOT; }
    ";"                    { atLineStart = false; return SEMICOLON; }
    "^"                    { atLineStart = false; return CARET; }
    "@"                    { atLineStart = false; return AT; }
    "?"                    { atLineStart = false; return QUESTION_MARK; }
    "_"                    { atLineStart = false; return UNDERSCORE; }
    "~"                    { atLineStart = false; return TILDE; }
}

// ─── Glob patterns ─────────────────────────────────────────────────────────────
<YYINITIAL, INTERP_EXPR> {
    {GLOB_PATTERN}         { atLineStart = false; return GLOB_PATTERN; }
}

// ─── Identifiers (catch-all for words, including Unicode) ──────────────────────
<YYINITIAL, INTERP_EXPR> {
    {IDENTIFIER}           { atLineStart = false; return IDENTIFIER; }
}

// ─── Bare words (anything else that looks like a path or unquoted word) ────────
<YYINITIAL, INTERP_EXPR> {
    [a-zA-Z0-9_./\\\-][a-zA-Z0-9_./\\\-:]*  { atLineStart = false; return BARE_WORD; }
}

// ─── Unicode catch-all (any non-ASCII char including supplementary planes) ─────
<YYINITIAL, INTERP_EXPR> {
    [^\x00-\x7F]+          { atLineStart = false; return IDENTIFIER; }
}

// ─── Fallback ──────────────────────────────────────────────────────────────────
[^]                        { atLineStart = false; return BAD_CHARACTER; }
