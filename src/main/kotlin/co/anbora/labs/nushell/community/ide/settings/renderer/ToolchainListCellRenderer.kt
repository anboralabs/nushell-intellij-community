package co.anbora.labs.nushell.community.ide.settings.renderer

import co.anbora.labs.nushell.community.ide.toolchain.NullToolchain
import co.anbora.labs.nushell.community.ide.toolchain.Toolchain
import com.intellij.openapi.ui.getPresentablePath
import com.intellij.ui.ColoredListCellRenderer
import com.intellij.ui.SimpleTextAttributes
import javax.swing.Icon
import javax.swing.JList
import kotlin.io.path.absolutePathString

class ToolchainListCellRenderer(
    private val nullToolchain: NullToolchain,
    private val iconProvider: () -> Icon
): ColoredListCellRenderer<Toolchain>() {
    override fun customizeCellRenderer(
        list: JList<out Toolchain>,
        value: Toolchain?,
        index: Int,
        selected: Boolean,
        hasFocus: Boolean,
    ) {
        when {
            value == null -> {
                append(nullToolchain.name())
                return
            }
            value is NullToolchain -> {
                append(value.name())
                return
            }
            value.isValid() -> {
                icon = iconProvider()
                append(value.version())
                append("  ")
                append(getPresentablePath(value.stdBinDir()?.absolutePathString() ?: ""), SimpleTextAttributes.GRAYED_ATTRIBUTES)
            }
            else -> {
                icon = iconProvider()
                append(value.version())
                append("  ")
                append(getPresentablePath(value.homePath()), SimpleTextAttributes.ERROR_ATTRIBUTES)
            }
        }
    }
}