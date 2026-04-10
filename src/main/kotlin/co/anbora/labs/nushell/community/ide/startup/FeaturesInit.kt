package co.anbora.labs.nushell.community.ide.startup

import co.anbora.labs.nushell.community.NuShellBundle
import co.anbora.labs.nushell.community.ide.actions.Install
import co.anbora.labs.nushell.community.ide.features.PluginFeatures
import co.anbora.labs.nushell.community.ide.notifications.Notifications
import com.intellij.ide.plugins.PluginManager
import com.intellij.notification.NotificationType
import com.intellij.openapi.extensions.PluginId
import com.intellij.openapi.project.Project
import com.intellij.openapi.startup.ProjectActivity

class FeaturesInit: ProjectActivity {

    val features = listOf(
        PluginFeatures(
            pluginId = "co.anbora.labs.nushell.lsp",
            pluginLink = "https://plugins.jetbrains.com/plugin/31106-nu-lsp",
            notificationText = "Nu LSP to get better support"
        ),
        PluginFeatures(
            pluginId = "co.anbora.labs.nushell.lint",
            pluginLink = "https://plugins.jetbrains.com/plugin/31165-nu-lint",
            notificationText = "Linter support"
        )
    )

    override suspend fun execute(project: Project) {
        val pluginManager = PluginManager.getInstance()

        for (feature in features) {

            val plugin = pluginManager.findEnabledPlugin(PluginId.getId(feature.pluginId))

            if (plugin == null) {
                val notification = Notifications.createNotification(
                    NuShellBundle.message("nushell.notification.title"),
                    feature.notificationText,
                    NotificationType.INFORMATION,
                    Install(feature)
                )

                Notifications.showNotification(notification, project)
            }
        }
    }
}