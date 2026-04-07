package co.anbora.labs.nushell.community.ide.settings.ui

import co.anbora.labs.nushell.community.ide.settings.renderer.ToolchainListCellRenderer
import co.anbora.labs.nushell.community.ide.toolchain.KnownToolchainStateService
import co.anbora.labs.nushell.community.ide.toolchain.NullToolchain
import co.anbora.labs.nushell.community.ide.toolchain.Toolchain
import co.anbora.labs.nushell.community.ide.toolchain.ToolchainService
import java.awt.event.ActionListener

data class ChooserComponentOptions(
    val browseActionListener: ActionListener,
    val onSelectAction: (Toolchain) -> Unit,
    val knownToolchainStateService: KnownToolchainStateService,
    val toolchainSettings: ToolchainService,
    val nullToolchainProvider: () -> NullToolchain,
    val renderer: ToolchainListCellRenderer
)
