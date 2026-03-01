package co.anbora.labs.nushell.community.ide.highlight

import co.anbora.labs.nushell.community.ide.color.NuShellColors
import co.anbora.labs.nushell.community.lang.core.*
import co.anbora.labs.nushell.community.lang.core.psi.NuShellTypes
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.psi.tree.IElementType

fun IElementType?.textAttributesKey(): TextAttributesKey? = map(this)?.textAttributesKey

private fun map(tokenType: IElementType?): NuShellColors? {
    return when (tokenType) {
        in KEYWORDS, NuShellTypes.BOOL_LITERAL -> NuShellColors.KEY_WORD
        NuShellTypes.VARIABLE -> NuShellColors.VARIABLES
        NuShellTypes.STRING_LITERAL, NuShellTypes.RAW_STRING_LITERAL,
        NuShellTypes.TRIPLE_STRING_LITERAL -> NuShellColors.STRINGS
        NuShellTypes.INTERP_STRING_START, NuShellTypes.INTERP_STRING_END,
        NuShellTypes.INTERP_STRING_CONTENT,
        NuShellTypes.INTERP_TRIPLE_STRING_START -> NuShellColors.STRINGS
        in NU_COMMENTS -> NuShellColors.COMMENTS
        in VARIABLES_DEFINITION -> NuShellColors.VARIABLES_DEFINITION
        in OPERATORS_WORDS -> NuShellColors.OPERATORS_WORDS
        in OPERATORS_SYMBOLS -> NuShellColors.OPERATORS_SYMBOLS
        NuShellTypes.COLON, NuShellTypes.DOUBLE_COLON -> NuShellColors.COLON
        NuShellTypes.DOT -> NuShellColors.DOT
        NuShellTypes.COMMA -> NuShellColors.COMMA
        NuShellTypes.SEMICOLON -> NuShellColors.SEMICOLON
        in NU_NUMBERS -> NuShellColors.NUMBERS
        in NU_FLAGS -> NuShellColors.FLAGS
        else -> null
    }
}
