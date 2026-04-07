package co.anbora.labs.nushell.community.ide.settings.mapper

import co.anbora.labs.nushell.community.ide.process.ExecutableToolchain
import co.anbora.labs.nushell.community.ide.toolchain.LocalToolchain
import co.anbora.labs.nushell.community.ide.toolchain.NullToolchain
import co.anbora.labs.nushell.community.ide.toolchain.Toolchain
import java.nio.file.Files
import java.nio.file.Path
import java.util.function.BiFunction
import java.util.function.Function
import kotlin.io.path.pathString

class PathMapper(
    private val versionMapper: BiFunction<String, ExecutableToolchain, String>,
    private val executableToolchain: ExecutableToolchain,
    private val nullToolchainProvider: () -> NullToolchain
): Function<String, Toolchain> {
    override fun apply(homePath: String): Toolchain {
        if (homePath == "") {
            return nullToolchainProvider()
        }

        val path = Path.of(homePath)
        if (!Files.isDirectory(path)) {
            return nullToolchainProvider()
        }
        return fromDirectory(path)
    }

    private fun fromDirectory(rootDir: Path): Toolchain {
        val version = versionMapper.apply(rootDir.pathString, executableToolchain)
        return LocalToolchain(version, rootDir, executableToolchain)
    }
}