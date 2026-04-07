package co.anbora.labs.nushell.community.ide.toolchain

import co.anbora.labs.nushell.community.ide.process.ExecutableToolchain
import java.nio.file.Files
import java.nio.file.Path
import kotlin.io.path.absolutePathString

class LocalToolchain(
    private val version: String,
    private val rootDir: Path,
    private val executableToolchain: ExecutableToolchain
): Toolchain {

    private val homePath = rootDir

    private val executable = rootDir.resolve(executableToolchain.getExecutable())

    override fun name(): String = version

    override fun version(): String = version

    override fun stdBinDir(): Path = executable

    override fun rootDir(): Path = rootDir

    override fun homePath(): String = rootDir().absolutePathString()

    override fun isValid(): Boolean {
        return Files.isExecutable(executable)
    }
}