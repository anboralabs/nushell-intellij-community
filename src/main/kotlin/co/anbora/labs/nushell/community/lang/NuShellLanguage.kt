package co.anbora.labs.nushell.community.lang

import com.intellij.lang.Language
import com.intellij.openapi.util.io.StreamUtil

object NuShellLanguage: Language("NuShell") {

    const val LANGUAGE_NAME = "NuShell"

    val LANGUAGE_DEMO_TEXT by lazy {
        val stream = javaClass.classLoader.getResourceAsStream("demo/script.nu")
            ?: error("No such file")
        val text = stream.bufferedReader().use { it.readText() }
        StreamUtil.convertSeparators(text)
    }

    private fun readResolve(): Any = NuShellLanguage
}