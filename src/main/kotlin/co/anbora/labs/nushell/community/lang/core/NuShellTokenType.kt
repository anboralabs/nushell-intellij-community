package co.anbora.labs.nushell.community.lang.core

import co.anbora.labs.nushell.community.lang.NuShellLanguage
import co.anbora.labs.nushell.community.lang.core.psi.NuShellTypes
import com.intellij.psi.tree.IElementType
import com.intellij.psi.tree.TokenSet

class NuShellTokenType(debugName: String) : IElementType(debugName, NuShellLanguage)

fun tokenSetOf(vararg tokens: IElementType) = TokenSet.create(*tokens)

val KEYWORDS = tokenSetOf(
    NuShellTypes.DEF, NuShellTypes.BREAK, NuShellTypes.CONTINUE, NuShellTypes.ELSE,
    NuShellTypes.IF, NuShellTypes.FOR, NuShellTypes.LOOP, NuShellTypes.RETURN,
    NuShellTypes.TRY, NuShellTypes.WHILE, NuShellTypes.NULL,
    NuShellTypes.MATCH, NuShellTypes.CATCH, NuShellTypes.MODULE, NuShellTypes.USE,
    NuShellTypes.EXPORT, NuShellTypes.EXPORT_ENV, NuShellTypes.HIDE, NuShellTypes.HIDE_ENV,
    NuShellTypes.OVERLAY, NuShellTypes.SOURCE, NuShellTypes.SOURCE_ENV, NuShellTypes.AS,
    NuShellTypes.DO, NuShellTypes.EXTERN,
    NuShellTypes.WHERE_KW, NuShellTypes.EACH_KW, NuShellTypes.COLLECT_KW, NuShellTypes.ERROR_KW
)

val VARIABLES_DEFINITION = tokenSetOf(
    NuShellTypes.MUT, NuShellTypes.CONST, NuShellTypes.LET, NuShellTypes.ALIAS
)

val OPERATORS_WORDS = tokenSetOf(
    NuShellTypes.MOD, NuShellTypes.IN, NuShellTypes.NOT_IN, NuShellTypes.NOT,
    NuShellTypes.AND, NuShellTypes.OR, NuShellTypes.XOR,
    NuShellTypes.BIT_OR, NuShellTypes.BIT_AND, NuShellTypes.BIT_XOR,
    NuShellTypes.BIT_SHL, NuShellTypes.BIT_SHR,
    NuShellTypes.BIT_NOT, NuShellTypes.BIT_ROL, NuShellTypes.BIT_ROR,
    NuShellTypes.STARTS_WITH, NuShellTypes.ENDS_WITH
)

val OPERATORS_SYMBOLS = tokenSetOf(
    NuShellTypes.PLUS, NuShellTypes.MINUS, NuShellTypes.STAR, NuShellTypes.SLASH,
    NuShellTypes.DOUBLE_SLASH, NuShellTypes.PERCENT, NuShellTypes.POWER,
    NuShellTypes.EQ, NuShellTypes.NEQ, NuShellTypes.LT, NuShellTypes.GT,
    NuShellTypes.LTE, NuShellTypes.GTE, NuShellTypes.REGEX_MATCH, NuShellTypes.REGEX_NOT_MATCH,
    NuShellTypes.AND_AND, NuShellTypes.OR_OR, NuShellTypes.EXCL,
    NuShellTypes.ASSIGN, NuShellTypes.PLUS_ASSIGN, NuShellTypes.MINUS_ASSIGN,
    NuShellTypes.STAR_ASSIGN, NuShellTypes.SLASH_ASSIGN,
    NuShellTypes.PERCENT_ASSIGN, NuShellTypes.POWER_ASSIGN, NuShellTypes.DOUBLE_SLASH_ASSIGN,
    NuShellTypes.PLUS_PLUS, NuShellTypes.MINUS_MINUS,
    NuShellTypes.DOTDOT, NuShellTypes.DOTDOT_EQ, NuShellTypes.DOTDOT_LT,
    NuShellTypes.PIPE, NuShellTypes.ERR_PIPE, NuShellTypes.OUT_ERR_PIPE, NuShellTypes.TEE_PIPE,
    NuShellTypes.REDIRECT_OUT, NuShellTypes.REDIRECT_ERR, NuShellTypes.REDIRECT_COMBINED,
    NuShellTypes.REDIRECT_APPEND_OUT, NuShellTypes.REDIRECT_APPEND_ERR, NuShellTypes.REDIRECT_APPEND_COMBINED,
    NuShellTypes.ARROW, NuShellTypes.FAT_ARROW
)

val NU_COMMENTS = tokenSetOf(
    NuShellTypes.EOL_COMMENT, NuShellTypes.EOL_DOC_COMMENT,
    NuShellTypes.SHEBANG_COMMENT, NuShellTypes.DOC_ATTRIBUTE
)

val NU_NUMBERS = tokenSetOf(
    NuShellTypes.INT_LITERAL, NuShellTypes.FLOAT_LITERAL,
    NuShellTypes.HEX_LITERAL, NuShellTypes.OCT_LITERAL, NuShellTypes.BIN_LITERAL,
    NuShellTypes.SCIENTIFIC_LITERAL, NuShellTypes.INF_LITERAL, NuShellTypes.NAN_LITERAL,
    NuShellTypes.DURATION_LITERAL, NuShellTypes.FILESIZE_LITERAL
)

val NU_STRINGS = tokenSetOf(
    NuShellTypes.STRING_LITERAL, NuShellTypes.RAW_STRING_LITERAL, NuShellTypes.TRIPLE_STRING_LITERAL,
    NuShellTypes.INTERP_STRING_START, NuShellTypes.INTERP_STRING_END, NuShellTypes.INTERP_STRING_CONTENT,
    NuShellTypes.INTERP_TRIPLE_STRING_START
)

val NU_FLAGS = tokenSetOf(NuShellTypes.LONG_FLAG, NuShellTypes.SHORT_FLAG)
