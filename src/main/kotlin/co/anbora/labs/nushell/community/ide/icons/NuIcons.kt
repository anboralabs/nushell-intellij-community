package co.anbora.labs.nushell.community.ide.icons

import com.intellij.openapi.util.IconLoader
import javax.swing.Icon

object NuIcons {

    val FILE = getIcon("nu_file.png")
    val NU_40 = getIcon("nu_40.svg")

    private fun getIcon(path: String): Icon {
        return IconLoader.findIcon("/icons/$path", NuIcons::class.java.classLoader) as Icon
    }
}