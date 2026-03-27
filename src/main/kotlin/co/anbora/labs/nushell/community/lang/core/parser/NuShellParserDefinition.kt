package co.anbora.labs.nushell.community.lang.core.parser

import co.anbora.labs.nushell.community.lang.NuShellLanguage
import co.anbora.labs.nushell.community.lang.core.psi.NuShellTypes
import co.anbora.labs.nushell.community.lang.lexer.NuLexer
import com.intellij.lang.ASTNode
import com.intellij.lang.PsiParser
import com.intellij.lexer.Lexer
import com.intellij.openapi.project.Project
import com.intellij.psi.FileViewProvider
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.psi.TokenType
import com.intellij.psi.tree.IFileElementType
import com.intellij.psi.tree.TokenSet

class NuShellParserDefinition : NuParserDefinition {

    private val WHITE_SPACES = TokenSet.create(TokenType.WHITE_SPACE, NuShellTypes.LINE_TERMINATOR)
    private val COMMENTS = TokenSet.create(
        NuShellTypes.EOL_COMMENT,
        NuShellTypes.EOL_DOC_COMMENT,
        NuShellTypes.SHEBANG_COMMENT
    )
    private val STRINGS = TokenSet.create(
        NuShellTypes.STRING_LITERAL,
        NuShellTypes.RAW_STRING_LITERAL,
        NuShellTypes.TRIPLE_STRING_LITERAL
    )
    private val file = IFileElementType(NuShellLanguage)

    override val order: Int = Int.MAX_VALUE

    override fun createLexer(project: Project?): Lexer = NuLexer()

    override fun createParser(project: Project?): PsiParser {
        return PsiParser { root, builder ->
            val rootMarker = builder.mark()
            while (!builder.eof()) {
                builder.advanceLexer()
            }
            rootMarker.done(root)
            return@PsiParser builder.treeBuilt
        }
    }

    override fun getFileNodeType(): IFileElementType = file

    override fun getWhitespaceTokens(): TokenSet = WHITE_SPACES

    override fun getCommentTokens(): TokenSet = COMMENTS

    override fun getStringLiteralElements(): TokenSet = STRINGS

    override fun createElement(node: ASTNode): PsiElement {
        return com.intellij.extapi.psi.ASTWrapperPsiElement(node)
    }

    override fun createFile(viewProvider: FileViewProvider): PsiFile {
        // Since we don't have a real PsiFile implementation yet, we might need a dummy or a basic one
        // But for commenter, usually it's enough to have the tokens classified.
        // However, createFile is mandatory.
        return object : com.intellij.extapi.psi.PsiFileBase(viewProvider, NuShellLanguage) {
            override fun getFileType(): com.intellij.openapi.fileTypes.FileType = viewProvider.virtualFile.fileType
        }
    }
}
