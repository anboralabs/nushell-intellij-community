package co.anbora.labs.nushell.community.lang.core.psi

import co.anbora.labs.nushell.community.lang.core.NuShellTokenType
import com.intellij.psi.tree.IElementType

/**
 * Manual token type definitions for the NuShell Community lexer.
 * Replaces the previously BNF-generated NuShellTypes.java.
 */
object NuShellTypes {

    // ── Whitespace & Line ──────────────────────────────────────────────
    @JvmField val LINE_TERMINATOR = NuShellTokenType("LINE_TERMINATOR")

    // ── Comments ───────────────────────────────────────────────────────
    @JvmField val EOL_COMMENT = NuShellTokenType("EOL_COMMENT")
    @JvmField val EOL_DOC_COMMENT = NuShellTokenType("EOL_DOC_COMMENT")
    @JvmField val SHEBANG_COMMENT = NuShellTokenType("SHEBANG_COMMENT")
    @JvmField val DOC_ATTRIBUTE = NuShellTokenType("DOC_ATTRIBUTE")

    // ── Number Literals ────────────────────────────────────────────────
    @JvmField val INT_LITERAL = NuShellTokenType("int_literal")
    @JvmField val FLOAT_LITERAL = NuShellTokenType("float_literal")
    @JvmField val HEX_LITERAL = NuShellTokenType("hex_literal")
    @JvmField val OCT_LITERAL = NuShellTokenType("oct_literal")
    @JvmField val BIN_LITERAL = NuShellTokenType("bin_literal")
    @JvmField val SCIENTIFIC_LITERAL = NuShellTokenType("scientific_literal")
    @JvmField val INF_LITERAL = NuShellTokenType("inf_literal")
    @JvmField val NAN_LITERAL = NuShellTokenType("nan_literal")

    // ── Duration & Filesize Literals ───────────────────────────────────
    @JvmField val DURATION_LITERAL = NuShellTokenType("duration_literal")
    @JvmField val FILESIZE_LITERAL = NuShellTokenType("filesize_literal")

    // ── Boolean & Null ─────────────────────────────────────────────────
    @JvmField val BOOL_LITERAL = NuShellTokenType("bool_literal")
    @JvmField val NULL = NuShellTokenType("null_kw")

    // ── String Literals ────────────────────────────────────────────────
    @JvmField val STRING_LITERAL = NuShellTokenType("string_literal")
    @JvmField val RAW_STRING_LITERAL = NuShellTokenType("raw_string_literal")
    @JvmField val TRIPLE_STRING_LITERAL = NuShellTokenType("triple_string_literal")

    // ── Interpolated Strings ───────────────────────────────────────────
    @JvmField val INTERP_STRING_START = NuShellTokenType("interp_string_start")
    @JvmField val INTERP_STRING_END = NuShellTokenType("interp_string_end")
    @JvmField val INTERP_STRING_CONTENT = NuShellTokenType("interp_string_content")
    @JvmField val INTERP_EXPR_START = NuShellTokenType("interp_expr_start")
    @JvmField val INTERP_EXPR_END = NuShellTokenType("interp_expr_end")
    @JvmField val INTERP_TRIPLE_STRING_START = NuShellTokenType("interp_triple_string_start")

    // ── Variables ──────────────────────────────────────────────────────
    @JvmField val VARIABLE = NuShellTokenType("variable")
    @JvmField val DOLLAR = NuShellTokenType("$")

    // ── Delimiters ─────────────────────────────────────────────────────
    @JvmField val L_PAREN = NuShellTokenType("(")
    @JvmField val R_PAREN = NuShellTokenType(")")
    @JvmField val L_BRACE = NuShellTokenType("{")
    @JvmField val R_BRACE = NuShellTokenType("}")
    @JvmField val L_BRACK = NuShellTokenType("[")
    @JvmField val R_BRACK = NuShellTokenType("]")
    @JvmField val COLON = NuShellTokenType(":")
    @JvmField val DOUBLE_COLON = NuShellTokenType("::")
    @JvmField val COMMA = NuShellTokenType(",")
    @JvmField val DOT = NuShellTokenType(".")
    @JvmField val SEMICOLON = NuShellTokenType(";")
    @JvmField val CARET = NuShellTokenType("^")
    @JvmField val AT = NuShellTokenType("@")
    @JvmField val UNDERSCORE = NuShellTokenType("_")
    @JvmField val AMPERSAND = NuShellTokenType("&")
    @JvmField val QUESTION_MARK = NuShellTokenType("?")
    @JvmField val QUESTION_DOT = NuShellTokenType("?.")
    @JvmField val TILDE = NuShellTokenType("~")
    @JvmField val BACKSLASH = NuShellTokenType("\\")
    @JvmField val DOTDOTDOT = NuShellTokenType("...")
    @JvmField val ARROW = NuShellTokenType("->")
    @JvmField val FAT_ARROW = NuShellTokenType("=>")
    @JvmField val DOUBLE_QUOTE = NuShellTokenType("\"")
    @JvmField val SINGLE_QUOTE = NuShellTokenType("'")

    // ── Arithmetic Operators ───────────────────────────────────────────
    @JvmField val PLUS = NuShellTokenType("+")
    @JvmField val MINUS = NuShellTokenType("-")
    @JvmField val STAR = NuShellTokenType("*")
    @JvmField val SLASH = NuShellTokenType("/")
    @JvmField val DOUBLE_SLASH = NuShellTokenType("//")
    @JvmField val PERCENT = NuShellTokenType("%")
    @JvmField val POWER = NuShellTokenType("**")

    // ── Comparison Operators ───────────────────────────────────────────
    @JvmField val EQ = NuShellTokenType("==")
    @JvmField val NEQ = NuShellTokenType("!=")
    @JvmField val LT = NuShellTokenType("<")
    @JvmField val GT = NuShellTokenType(">")
    @JvmField val LTE = NuShellTokenType("<=")
    @JvmField val GTE = NuShellTokenType(">=")
    @JvmField val REGEX_MATCH = NuShellTokenType("=~")
    @JvmField val REGEX_NOT_MATCH = NuShellTokenType("!~")

    // ── Logical Operators (symbols) ────────────────────────────────────
    @JvmField val AND_AND = NuShellTokenType("&&")
    @JvmField val OR_OR = NuShellTokenType("||")
    @JvmField val EXCL = NuShellTokenType("!")

    // ── Assignment Operators ───────────────────────────────────────────
    @JvmField val ASSIGN = NuShellTokenType("=")
    @JvmField val PLUS_ASSIGN = NuShellTokenType("+=")
    @JvmField val MINUS_ASSIGN = NuShellTokenType("-=")
    @JvmField val STAR_ASSIGN = NuShellTokenType("*=")
    @JvmField val SLASH_ASSIGN = NuShellTokenType("/=")
    @JvmField val PERCENT_ASSIGN = NuShellTokenType("%=")
    @JvmField val POWER_ASSIGN = NuShellTokenType("**=")
    @JvmField val DOUBLE_SLASH_ASSIGN = NuShellTokenType("//=")
    @JvmField val PLUS_PLUS = NuShellTokenType("++")
    @JvmField val MINUS_MINUS = NuShellTokenType("--")

    // ── Range Operators ────────────────────────────────────────────────
    @JvmField val DOTDOT = NuShellTokenType("..")
    @JvmField val DOTDOT_EQ = NuShellTokenType("..=")
    @JvmField val DOTDOT_LT = NuShellTokenType("..<")

    // ── Pipeline Operators ─────────────────────────────────────────────
    @JvmField val PIPE = NuShellTokenType("|")
    @JvmField val ERR_PIPE = NuShellTokenType("e>|")
    @JvmField val OUT_ERR_PIPE = NuShellTokenType("o+e>|")
    @JvmField val TEE_PIPE = NuShellTokenType("|>")

    // ── Redirection Operators ──────────────────────────────────────────
    @JvmField val REDIRECT_OUT = NuShellTokenType("out>")
    @JvmField val REDIRECT_ERR = NuShellTokenType("err>")
    @JvmField val REDIRECT_COMBINED = NuShellTokenType("out+err>")
    @JvmField val REDIRECT_APPEND_OUT = NuShellTokenType("out>>")
    @JvmField val REDIRECT_APPEND_ERR = NuShellTokenType("err>>")
    @JvmField val REDIRECT_APPEND_COMBINED = NuShellTokenType("out+err>>")

    // ── Keyword Operator Words ─────────────────────────────────────────
    @JvmField val AND = NuShellTokenType("and_op")
    @JvmField val OR = NuShellTokenType("or_op")
    @JvmField val NOT = NuShellTokenType("not_op")
    @JvmField val XOR = NuShellTokenType("xor_op")
    @JvmField val IN = NuShellTokenType("in_op")
    @JvmField val NOT_IN = NuShellTokenType("not_in_op")
    @JvmField val MOD = NuShellTokenType("mod_op")
    @JvmField val BIT_AND = NuShellTokenType("bit_and_op")
    @JvmField val BIT_OR = NuShellTokenType("bit_or_op")
    @JvmField val BIT_XOR = NuShellTokenType("bit_xor_op")
    @JvmField val BIT_SHL = NuShellTokenType("bit_shl_op")
    @JvmField val BIT_SHR = NuShellTokenType("bit_shr_op")
    @JvmField val BIT_NOT = NuShellTokenType("bit_not_op")
    @JvmField val BIT_ROL = NuShellTokenType("bit_rol_op")
    @JvmField val BIT_ROR = NuShellTokenType("bit_ror_op")
    @JvmField val STARTS_WITH = NuShellTokenType("starts_with_op")
    @JvmField val ENDS_WITH = NuShellTokenType("ends_with_op")

    // ── Definition Keywords ────────────────────────────────────────────
    @JvmField val DEF = NuShellTokenType("def_kw")
    @JvmField val LET = NuShellTokenType("let_kw")
    @JvmField val MUT = NuShellTokenType("mut_kw")
    @JvmField val CONST = NuShellTokenType("const_kw")
    @JvmField val ALIAS = NuShellTokenType("alias_kw")
    @JvmField val EXTERN = NuShellTokenType("extern_kw")

    // ── Control Flow Keywords ──────────────────────────────────────────
    @JvmField val IF = NuShellTokenType("if_kw")
    @JvmField val ELSE = NuShellTokenType("else_kw")
    @JvmField val MATCH = NuShellTokenType("match_kw")
    @JvmField val FOR = NuShellTokenType("for_kw")
    @JvmField val WHILE = NuShellTokenType("while_kw")
    @JvmField val LOOP = NuShellTokenType("loop_kw")
    @JvmField val BREAK = NuShellTokenType("break_kw")
    @JvmField val CONTINUE = NuShellTokenType("continue_kw")
    @JvmField val RETURN = NuShellTokenType("return_kw")

    // ── Error Keywords ─────────────────────────────────────────────────
    @JvmField val TRY = NuShellTokenType("try_kw")
    @JvmField val CATCH = NuShellTokenType("catch_kw")

    // ── Module Keywords ────────────────────────────────────────────────
    @JvmField val MODULE = NuShellTokenType("module_kw")
    @JvmField val USE = NuShellTokenType("use_kw")
    @JvmField val EXPORT = NuShellTokenType("export_kw")
    @JvmField val EXPORT_ENV = NuShellTokenType("export_env_kw")
    @JvmField val HIDE = NuShellTokenType("hide_kw")
    @JvmField val HIDE_ENV = NuShellTokenType("hide_env_kw")
    @JvmField val OVERLAY = NuShellTokenType("overlay_kw")
    @JvmField val SOURCE = NuShellTokenType("source_kw")
    @JvmField val SOURCE_ENV = NuShellTokenType("source_env_kw")
    @JvmField val AS = NuShellTokenType("as_kw")

    // ── Other Keywords ─────────────────────────────────────────────────
    @JvmField val DO = NuShellTokenType("do_kw")
    @JvmField val WHERE_KW = NuShellTokenType("where_kw")
    @JvmField val EACH_KW = NuShellTokenType("each_kw")
    @JvmField val COLLECT_KW = NuShellTokenType("collect_kw")
    @JvmField val ERROR_KW = NuShellTokenType("error_kw")

    // ── Flags ──────────────────────────────────────────────────────────
    @JvmField val LONG_FLAG = NuShellTokenType("long_flag")
    @JvmField val SHORT_FLAG = NuShellTokenType("short_flag")

    // ── Identifiers ────────────────────────────────────────────────────
    @JvmField val IDENTIFIER = NuShellTokenType("identifier")
    @JvmField val OTHER_IDENTIFIER = NuShellTokenType("other_identifier")
}
