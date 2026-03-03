package co.anbora.labs.nushell.community.ide.settings

import com.intellij.openapi.options.Configurable
import com.intellij.openapi.options.ConfigurableProvider

class NuShellSettingsConfigurableProvider : ConfigurableProvider() {

    override fun canCreateConfigurable(): Boolean =
        NuShellConfigurableProvider.EP_NAME.extensionList.isNotEmpty()

    override fun createConfigurable(): Configurable = NuShellConfigurable()
}
