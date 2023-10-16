package co.anbora.labs.nushell.community.lang.core.psi

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElement

interface NuShellElement: PsiElement

abstract class NuShellElementImpl(
    node: ASTNode
) : ASTWrapperPsiElement(node), NuShellElement