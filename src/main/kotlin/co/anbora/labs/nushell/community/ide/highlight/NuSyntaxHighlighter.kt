package co.anbora.labs.nushell.community.ide.highlight

import co.anbora.labs.nushell.community.lang.lexer.NuLexer
import com.intellij.lexer.Lexer
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase
import com.intellij.psi.tree.IElementType

class NuSyntaxHighlighter: SyntaxHighlighterBase() {
    override fun getHighlightingLexer(): Lexer = NuLexer()

    override fun getTokenHighlights(
        tokenType: IElementType?
    ): Array<TextAttributesKey> = pack(tokenType.textAttributesKey())
}