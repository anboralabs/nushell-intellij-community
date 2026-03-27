package co.anbora.labs.nushell.community.ide.commenter

import com.intellij.codeInsight.generation.actions.CommentByLineCommentAction
import com.intellij.testFramework.fixtures.BasePlatformTestCase

class NuCommenterTest : BasePlatformTestCase() {

    fun testLineComment() {
        myFixture.configureByText("test.nu", "<caret>ls")
        val commentAction = CommentByLineCommentAction()
        commentAction.actionPerformedImpl(project, myFixture.editor)
        myFixture.checkResult("#ls")
    }

    fun testUncomment() {
        myFixture.configureByText("test.nu", "<caret># ls")
        val commentAction = CommentByLineCommentAction()
        commentAction.actionPerformedImpl(project, myFixture.editor)
        myFixture.checkResult(" ls")
    }
}
