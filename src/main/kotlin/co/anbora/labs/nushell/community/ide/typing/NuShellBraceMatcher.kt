package co.anbora.labs.nushell.community.ide.typing

import co.anbora.labs.nushell.community.lang.core.psi.NuShellTypes.*
import com.intellij.lang.BracePair
import com.intellij.lang.PairedBraceMatcher
import com.intellij.psi.PsiFile
import com.intellij.psi.tree.IElementType

private val bracePairs = arrayOf(
    BracePair(L_PAREN, R_PAREN, false),
    BracePair(L_BRACK, R_BRACK, false),
    BracePair(L_BRACE, R_BRACE, true)
)

class NuShellBraceMatcher: PairedBraceMatcher {
    override fun getPairs(): Array<BracePair> = bracePairs

    override fun isPairedBracesAllowedBeforeType(
        lbraceType: IElementType,
        contextType: IElementType?
    ): Boolean = true

    override fun getCodeConstructStart(
        file: PsiFile?,
        openingBraceOffset: Int
    ): Int = openingBraceOffset
}