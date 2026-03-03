package co.anbora.labs.nushell.community.ide.settings

import co.anbora.labs.nushell.community.NuShellBundle
import com.intellij.openapi.options.Configurable
import com.intellij.ui.dsl.builder.AlignX
import com.intellij.ui.dsl.builder.panel
import javax.swing.BorderFactory
import javax.swing.JComponent

class NuShellConfigurable : Configurable {

    private val providers: List<NuShellConfigurableProvider>
        get() = NuShellConfigurableProvider.EP_NAME.extensionList

    override fun getDisplayName(): String = NuShellBundle.message("nushell.settings.display.name")

    override fun createComponent(): JComponent {
        return panel {
            for (provider in providers) {
                separator()
                group(provider.getDisplayName()) {
                    row {
                        cell(provider.getPanel())
                            .align(AlignX.FILL)
                    }
                }
            }
        }.withBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0))
    }

    override fun isModified(): Boolean = providers.any { it.isModified() }

    override fun apply() {
        providers.forEach { it.apply() }
    }

    override fun reset() {
        providers.forEach { it.reset() }
    }
}
