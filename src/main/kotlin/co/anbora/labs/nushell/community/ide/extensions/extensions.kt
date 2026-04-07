package co.anbora.labs.nushell.community.ide.extensions

import com.intellij.openapi.vfs.VirtualFile
import com.intellij.ui.DocumentAdapter
import java.nio.file.InvalidPathException
import java.nio.file.Path
import java.nio.file.Paths
import javax.swing.JTextField
import javax.swing.event.DocumentEvent

fun String.toPath(): Path = Paths.get(this)

fun String.toPathOrNull(): Path? = pathOrNull(this::toPath)

val VirtualFile.pathAsPath: Path get() = Paths.get(path)

private inline fun pathOrNull(block: () -> Path): Path? {
    return try {
        block()
    } catch (e: InvalidPathException) {
        null
    }
}

fun JTextField.addTextChangeListener(listener: (DocumentEvent) -> Unit) {
    document.addDocumentListener(
        object : DocumentAdapter() {
            override fun textChanged(e: DocumentEvent) {
                listener(e)
            }
        }
    )
}