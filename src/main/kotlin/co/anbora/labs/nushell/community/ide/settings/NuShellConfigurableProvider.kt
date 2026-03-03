package co.anbora.labs.nushell.community.ide.settings

import com.intellij.openapi.extensions.ExtensionPointName
import javax.swing.JComponent

interface NuShellConfigurableProvider {

    fun getId(): String

    fun getDisplayName(): String

    fun getPanel(): JComponent

    fun isModified(): Boolean

    fun apply()

    fun reset()

    fun getOrder(): Int = 0

    companion object {
        val EP_NAME = ExtensionPointName.create<NuShellConfigurableProvider>(
            "co.anbora.labs.nushell.community.configurableProvider"
        )
    }
}
