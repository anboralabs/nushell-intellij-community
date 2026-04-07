package co.anbora.labs.nushell.community.ide.actions

import co.anbora.labs.nushell.community.ide.features.PluginFeatures
import com.intellij.ide.BrowserUtil
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.project.DumbAwareAction

class Install(val plugin: PluginFeatures): DumbAwareAction("Install") {
    override fun actionPerformed(action: AnActionEvent) {
        BrowserUtil.browse(plugin.pluginLink)
    }
}