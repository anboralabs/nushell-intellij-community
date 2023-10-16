package co.anbora.labs.nushell.community.lang

import co.anbora.labs.nushell.community.ide.icons.NuIcons
import co.anbora.labs.nushell.community.lang.NuShellLanguage.LANGUAGE_NAME
import com.intellij.openapi.fileTypes.LanguageFileType
import javax.swing.Icon

object NuShellFileType: LanguageFileType(NuShellLanguage) {
    override fun getName(): String = LANGUAGE_NAME

    override fun getDescription(): String = "NuShell Script files"

    override fun getDefaultExtension(): String = ""

    override fun getIcon(): Icon = NuIcons.FILE
}