package co.anbora.labs.nushell.community.lang

import com.intellij.lang.Language
import com.intellij.openapi.util.io.StreamUtil

object NuShellLanguage: Language("nu_community_lang") {

    const val LANGUAGE_NAME = "NuShell_Community"

    val LANGUAGE_DEMO_TEXT by lazy {
        val stream = javaClass.classLoader.getResourceAsStream("demo/script.nu")
            ?: error("No such file")
        val text = stream.bufferedReader().use { it.readText() }
        StreamUtil.convertSeparators(text)
    }

    private fun readResolve(): Any = NuShellLanguage
}