package co.anbora.labs.nushell.community.ide.notifications

import co.anbora.labs.nushell.community.NuShellBundle
import co.anbora.labs.nushell.community.ide.icons.NuIcons
import com.intellij.notification.Notification
import com.intellij.notification.NotificationGroupManager
import com.intellij.notification.NotificationType
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.project.Project
import javax.swing.Icon

object Notifications {
    @JvmStatic
    fun createNotification(
        title: String,
        content: String,
        type: NotificationType,
        notificationGroup: String,
        icon: Icon,
        vararg actions: AnAction
    ): Notification {
        val notification = NotificationGroupManager.getInstance()
            .getNotificationGroup(notificationGroup)
            .createNotification(content, type)
            .setTitle(title)
            .setIcon(icon)

        for (action in actions) {
            notification.addAction(action)
        }

        return notification
    }

    @JvmStatic
    fun showNotification(notification: Notification, project: Project?) {
        try {
            notification.notify(project)
        } catch (e: Exception) {
            notification.notify(project)
        }
    }

    @JvmStatic
    fun createNotification(
        title: String,
        content: String,
        type: NotificationType,
        vararg actions: AnAction
    ): Notification {
        return createNotification(
            title,
            content,
            type,
            NuShellBundle.message("nushell.notification.key"),
            NuIcons.NU_40,
            *actions
        )
    }
}