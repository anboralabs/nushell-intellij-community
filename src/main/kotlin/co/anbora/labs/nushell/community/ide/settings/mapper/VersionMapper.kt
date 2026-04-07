package co.anbora.labs.nushell.community.ide.settings.mapper

import co.anbora.labs.nushell.community.ide.extensions.toPath
import co.anbora.labs.nushell.community.ide.process.CommandLineExecutor
import co.anbora.labs.nushell.community.ide.process.ExecutableToolchain
import java.util.function.BiFunction
import java.util.regex.Matcher
import java.util.regex.Pattern
import kotlin.io.path.exists

class VersionMapper: BiFunction<String, ExecutableToolchain, String> {

    val UNDEFINED_VERSION = "N/A"

    override fun apply(
        path: String,
        executableToolchain: ExecutableToolchain
    ): String {
        if (path.isBlank()) {
            return UNDEFINED_VERSION
        }

        val exePath = "$path/${executableToolchain.getExecutable()}"
        if (!exePath.toPath().exists()) {
            return UNDEFINED_VERSION
        }

        val result = CommandLineExecutor.executeAndReturnOutput(exePath, "--version")

        val pattern = Pattern.compile("(\\d+(.\\d+)*)")
        val matcher: Matcher = pattern.matcher(result)

        var version = ""

        if (matcher.find()) {
            version = matcher.group()
        }

        return version.trim().ifEmpty { UNDEFINED_VERSION }
    }
}