package co.anbora.labs.nushell.community.ide.color

import com.intellij.lang.annotation.HighlightSeverity
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors
import com.intellij.openapi.editor.HighlighterColors
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.editor.markup.TextAttributes
import com.intellij.openapi.options.colors.AttributesDescriptor
import java.awt.Color
import java.awt.Font

enum class NuShellColors(humanName: String, default: TextAttributesKey) {

    KEY_WORD("Keywords", DefaultLanguageHighlighterColors.KEYWORD),
    VARIABLES("Variables//References", TextAttributesKey.createTextAttributesKey("DEFAULT_ENVS", DefaultLanguageHighlighterColors.NUMBER)),
    VARIABLES_DEFINITION("Variables//Keywords", TextAttributesKey.createTextAttributesKey("DEFAULT_ENVS", DefaultLanguageHighlighterColors.NUMBER)),
    STRINGS("Literals//Strings", DefaultLanguageHighlighterColors.STRING),
    NUMBERS("Literals//Numbers", DefaultLanguageHighlighterColors.NUMBER),
    COMMENTS("Comments", DefaultLanguageHighlighterColors.LINE_COMMENT),
    OPERATORS_WORDS(
        "Operators//Words",
        TextAttributesKey.createTempTextAttributesKey(
            "OPERATORS_WORDS",
            TextAttributes(Color.decode("#8A653B"), null, null, null, Font.PLAIN)
        )
    ),
    COLON("Operators//Colon", DefaultLanguageHighlighterColors.SEMICOLON),
    DOT("Operators//Dot", DefaultLanguageHighlighterColors.DOT),
    COMMA("Operators//Comma", DefaultLanguageHighlighterColors.COMMA);

    val textAttributesKey = TextAttributesKey.createTextAttributesKey("co.anbora.labs.nushell.community.$name", default)
    val attributesDescriptor = AttributesDescriptor(humanName, textAttributesKey)
    val testSeverity: HighlightSeverity = HighlightSeverity(name, HighlightSeverity.INFORMATION.myVal)
}