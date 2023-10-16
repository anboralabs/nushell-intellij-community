// Copyright 2000-2020 JetBrains s.r.o. and other contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
package co.anbora.labs.nushell.community.lang;

import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;

import static com.intellij.psi.TokenType.BAD_CHARACTER;
import static com.intellij.psi.TokenType.WHITE_SPACE;
import static co.anbora.labs.nushell.community.lang.core.psi.NuShellTypes.*;
import static co.anbora.labs.nushell.community.lang.NuShellParserDefinition.*;

%%

%{
    /**
        * Dedicated storage for starting position of some previously successful
        * match
    */
    private int zzPostponedMarkedPos = -1;

    /**
        * Dedicated nested-comment level counter
    */
    private int zzNestedCommentLevel = 0;
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

%s IN_BLOCK_COMMENT

///////////////////////////////////////////////////////////////////////////////////////////////////
// Whitespaces
///////////////////////////////////////////////////////////////////////////////////////////////////
EOL_WS           = \n | \r | \r\n
LINE_WS          = [\ \t]
WHITE_SPACE_CHAR = {LINE_WS}
WHITE_SPACE      = {WHITE_SPACE_CHAR}+

///////////////////////////////////////////////////////////////////////////////////////////////////
// Comments
///////////////////////////////////////////////////////////////////////////////////////////////////
EOL_DOC_LINE  = {LINE_WS}*("#".*) {EOL_WS}?
LINE_COMMENT = "#"[^\r\n]* {EOL_WS}?

///////////////////////////////////////////////////////////////////////////////////////////////////
// Literals
///////////////////////////////////////////////////////////////////////////////////////////////////
BOOL_LITERAL=(true)|(false)
NUMBER_LITERAL=[0-9]+(\.[0-9]*)?
STRING_LITERAL=('([^'\\]|\\.)*'|\"([^\"\\]|\\.)*\")
IDENTIFIER=[a-zA-Z_\-0-9]+
VARIABLE="$"{IDENTIFIER}

%%

<YYINITIAL> {
      {EOL_WS}+            { return LINE_TERMINATOR; }
      {WHITE_SPACE}        { return WHITE_SPACE; }
      {LINE_COMMENT}       { return EOL_COMMENT; }
}

<YYINITIAL> {
    // operators
    "{"                        { return L_BRACE; }
    "}"                        { return R_BRACE; }
    "["                        { return L_BRACK; }
    "]"                        { return R_BRACK; }
    "("                        { return L_PAREN; }
    ")"                        { return R_PAREN; }
    //"&"                        { return AND; }
    //"|"                        { return OR; }
    ":"                        { return COLON; }
    ","                        { return COMMA; }
    "."                        { return DOT; }
    "$"                        { return DOLLAR; }
    "?"                        { return QUESTION_MARK; }
    "\""                       { return DOUBLE_QUOTE; }
    "\'"                       { return SINGLE_QUOTE; }

    // operators words
    "mod"                      { return MOD; }
    "in"                       { return IN; }
    "not-in"                   { return NOT_IN; }
    "not"                      { return NOT; }
    "and"                      { return AND; }
    "or"                       { return OR; }
    "xor"                      { return XOR; }
    "bit-or"                   { return BIT_OR; }
    "bit-and"                  { return BIT_AND; }
    "bit-xor"                  { return BIT_XOR; }
    "bit-shl"                  { return BIT_SHL; }
    "bit-shr"                  { return BIT_SHR; }
    "starts-with"              { return STARTS_WITH; }
    "ends-with"                { return ENDS_WITH; }

    // keywords
    "def"                      { return DEF; }

    // control keywords
    "break"                    { return BREAK; }
    "continue"                 { return CONTINUE; }
    "else"                     { return ELSE; }
    "if"                       { return IF; }
    "for"                      { return FOR; }
    "loop"                     { return LOOP; }
    "return"                   { return RETURN; }
    "try"                      { return TRY; }
    "while"                    { return WHILE; }

    // define variables
    "mut"                      { return MUT; }
    "const"                    { return CONST; }
    "let"                      { return LET; }
    "alias"                    { return ALIAS; }
    "null"                     { return NULL; }

    // operators symbols

    {BOOL_LITERAL}             { return BOOL_LITERAL; }
    {NUMBER_LITERAL}           { return NUMBER_LITERAL; }
    {STRING_LITERAL}           { return STRING_LITERAL; }
    {VARIABLE}                 { return VARIABLE; }
    {IDENTIFIER}               { return IDENTIFIER; }
}

[^] { return OTHER_IDENTIFIER; }
