package co.anbora.labs.nushell.community.ide.highlight

import co.anbora.labs.nushell.community.ide.color.NuShellColors
import co.anbora.labs.nushell.community.lang.core.KEYWORDS
import co.anbora.labs.nushell.community.lang.core.NU_COMMENTS
import co.anbora.labs.nushell.community.lang.core.OPERATORS_WORDS
import co.anbora.labs.nushell.community.lang.core.VARIABLES_DEFINITION
import co.anbora.labs.nushell.community.lang.core.psi.NuShellTypes.*
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.psi.tree.IElementType

fun IElementType?.textAttributesKey(): TextAttributesKey? = map(this)?.textAttributesKey

private fun map(tokenType: IElementType?): NuShellColors? {
    return when (tokenType) {
        in KEYWORDS, BOOL_LITERAL -> NuShellColors.KEY_WORD
        VARIABLE -> NuShellColors.VARIABLES
        STRING_LITERAL -> NuShellColors.STRINGS
        in NU_COMMENTS -> NuShellColors.COMMENTS
        in VARIABLES_DEFINITION -> NuShellColors.VARIABLES_DEFINITION
        in OPERATORS_WORDS -> NuShellColors.OPERATORS_WORDS
        COLON -> NuShellColors.COLON
        DOT -> NuShellColors.DOT
        COMMA -> NuShellColors.COMMA
        NUMBER_LITERAL -> NuShellColors.NUMBERS
        else -> null
    }
}
