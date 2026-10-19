package co.anbora.labs.nushell.community.ide.settings.ui

import co.anbora.labs.nushell.community.ide.extensions.addTextChangeListener
import co.anbora.labs.nushell.community.ide.extensions.pathAsPath
import com.intellij.openapi.Disposable
import com.intellij.openapi.application.EDT
import com.intellij.openapi.application.ModalityState
import com.intellij.openapi.application.asContextElement
import com.intellij.openapi.fileChooser.FileChooser
import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory
import com.intellij.openapi.ui.ComboBoxWithWidePopup
import com.intellij.openapi.ui.ComponentWithBrowseButton
import com.intellij.openapi.util.Disposer
import com.intellij.ui.AnimatedIcon
import com.intellij.ui.ComboboxSpeedSearch
import com.intellij.ui.components.fields.ExtendableTextComponent
import com.intellij.ui.components.fields.ExtendableTextField
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.nio.file.Path
import javax.swing.plaf.basic.BasicComboBoxEditor
import kotlin.io.path.pathString

class ToolchainPathChoosingComboBox(onTextChanged: () -> Unit = {}) :
    ComponentWithBrowseButton<ComboBoxWithWidePopup<Path>>(ComboBoxWithWidePopup(), null) {

    private val editor: BasicComboBoxEditor = object : BasicComboBoxEditor() {
        override fun createEditorComponent(): ExtendableTextField = ExtendableTextField()
    }

    private val pathTextField: ExtendableTextField
        get() = childComponent.editor.editorComponent as ExtendableTextField

    private val busyIconExtension: ExtendableTextComponent.Extension =
        ExtendableTextComponent.Extension { AnimatedIcon.Default.INSTANCE }

    var selectedPath: String?
        get() = pathTextField.text
        set(value) {
            pathTextField.text = value.orEmpty()
        }

    init {
        ComboboxSpeedSearch.installOn(childComponent)
        childComponent.editor = editor
        childComponent.isEditable = true

        addActionListener {
            val descriptor = FileChooserDescriptorFactory.createSingleFolderDescriptor()
            FileChooser.chooseFile(descriptor, null, null) { file ->
                childComponent.selectedItem = file.pathAsPath
            }
        }

        pathTextField.addTextChangeListener { onTextChanged() }
    }

    private fun setBusy(busy: Boolean) {
        if (busy) {
            pathTextField.addExtension(busyIconExtension)
        } else {
            pathTextField.removeExtension(busyIconExtension)
        }
        repaint()
    }

    /**
     * Obtains a list of toolchains on the IO dispatcher using [toolchainObtainer], then fills the combobox on the EDT.
     * The work is cancelled when [parentDisposable] is disposed.
     */
    fun addToolchainsAsync(parentDisposable: Disposable, toolchainObtainer: () -> List<Path>) {
        val scope = CoroutineScope(SupervisorJob() + Dispatchers.EDT)
        Disposer.register(parentDisposable) { scope.cancel() }

        setBusy(true)
        scope.launch(ModalityState.any().asContextElement()) {
            val toolchains = withContext(Dispatchers.IO) {
                try {
                    toolchainObtainer()
                } catch (e: Exception) {
                    emptyList()
                }
            }
            setBusy(false)
            childComponent.removeAllItems()
            toolchains.forEach(childComponent::addItem)
            selectedPath = selectedPath?.ifEmpty { null } ?: (toolchains.firstOrNull()?.pathString ?: "")
        }
    }
}
