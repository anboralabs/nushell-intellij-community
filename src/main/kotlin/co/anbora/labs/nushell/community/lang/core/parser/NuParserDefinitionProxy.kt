package co.anbora.labs.nushell.community.lang.core.parser

import com.intellij.lang.ASTNode
import com.intellij.lang.ParserDefinition
import com.intellij.lang.PsiParser
import com.intellij.lexer.Lexer
import com.intellij.openapi.project.Project
import com.intellij.psi.FileViewProvider
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.psi.tree.IFileElementType
import com.intellij.psi.tree.TokenSet

class NuParserDefinitionProxy : ParserDefinition {

    private val delegate: ParserDefinition
        get() = NuParserDefinition.EP_NAME.extensionList.minByOrNull { it.order } ?: NuShellParserDefinition()

    override fun createLexer(project: Project?): Lexer = delegate.createLexer(project)

    override fun createParser(project: Project?): PsiParser = delegate.createParser(project)

    override fun getFileNodeType(): IFileElementType = delegate.fileNodeType

    override fun getWhitespaceTokens(): TokenSet = delegate.whitespaceTokens

    override fun getCommentTokens(): TokenSet = delegate.commentTokens

    override fun getStringLiteralElements(): TokenSet = delegate.stringLiteralElements

    override fun createElement(node: ASTNode?): PsiElement = delegate.createElement(node)

    override fun createFile(viewProvider: FileViewProvider): PsiFile = delegate.createFile(viewProvider)

    override fun spaceExistenceTypeBetweenTokens(left: ASTNode?, right: ASTNode?): ParserDefinition.SpaceRequirements {
        return delegate.spaceExistenceTypeBetweenTokens(left, right)
    }
}
