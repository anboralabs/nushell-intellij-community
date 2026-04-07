package co.anbora.labs.nushell.community.ide.settings.ui

import co.anbora.labs.nushell.community.ide.toolchain.NullToolchain
import co.anbora.labs.nushell.community.ide.toolchain.Toolchain
import com.intellij.openapi.ui.ComboBox
import com.intellij.openapi.ui.ComponentWithBrowseButton
import com.intellij.util.ui.SwingHelper
import com.jgoodies.common.base.Objects
import java.awt.event.ItemEvent
import javax.swing.DefaultComboBoxModel

class ToolchainChooserComponent(
    private val options: ChooserComponentOptions
): ComponentWithBrowseButton<ComboBox<Toolchain>>(ComboBox<Toolchain>(), options.browseActionListener) {

    private val toolchainSettings = options.toolchainSettings
    private val onSelectAction: (Toolchain) -> Unit = options.onSelectAction

    private val comboBox = childComponent
    private val knownToolchains get() = options.knownToolchainStateService.knownToolchains()
    private var knownToolchainInfos = options.knownToolchainStateService.knownToolchains()

    private var myLastSelectedItem: Toolchain = toolchainSettings.toolchain()
    private val myModel: ToolchainComboBoxModel = ToolchainComboBoxModel()

    init {
        this.comboBox.setModel(this.myModel)
        this.comboBox.renderer = options.renderer
        this.comboBox.setMinimumAndPreferredWidth(0)
        this.myModel.addElement(toolchainSettings.toolchain())
        this.myModel.selectedItem = toolchainSettings.toolchain()
        this.updateDropDownList()
        this.comboBox.addItemListener { e: ItemEvent ->
            if (e.stateChange == 1) {
                this.handleSelectedItemChange()
            }
        }
    }

    private fun updateDropDownList() {
        val toolchains: LinkedHashSet<Toolchain> = LinkedHashSet(knownToolchainInfos)
        toolchains.add(toolchainSettings.toolchain())
        SwingHelper.updateItems(this.comboBox, toolchains.toList(), null)

        val selected = toolchainSettings.toolchain()

        if (!Objects.equals(this.comboBox.selectedItem, selected)) {
            this.comboBox.selectedItem = selected
            this.handleSelectedItemChange()
        }
    }

    private fun handleSelectedItemChange() {
        when (val selected = this.getToolchainRef()) {
            is NullToolchain -> Unit
            else -> {
                if (this.myLastSelectedItem != selected && selected.isValid()) {
                    this.myLastSelectedItem = selected
                    this@ToolchainChooserComponent.onSelectAction(selected)
                }
            }
        }
    }

    private fun getToolchainRef(): Toolchain {
        var ref = this.comboBox.selectedItem as? Toolchain
        if (ref == null) {
            ref = options.nullToolchainProvider()
        }
        return ref
    }

    fun selectedToolchain(): Toolchain? {
        return comboBox.selectedItem as? Toolchain
    }

    fun refresh() {
        comboBox.removeAllItems()
        knownToolchainInfos = knownToolchains

        updateDropDownList()
    }

    fun select(location: String) {
        if (location.isEmpty()) {
            comboBox.selectedItem = options.nullToolchainProvider()
            return
        }

        val infoToSelect = knownToolchainInfos.find { it.homePath() == location } ?: return
        comboBox.selectedItem = infoToSelect
    }

    private inner class ToolchainComboBoxModel: DefaultComboBoxModel<Toolchain>()
}
