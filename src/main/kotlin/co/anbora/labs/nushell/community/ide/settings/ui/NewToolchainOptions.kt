package co.anbora.labs.nushell.community.ide.settings.ui

import co.anbora.labs.nushell.community.ide.process.ExecutableToolchain
import co.anbora.labs.nushell.community.ide.toolchain.KnownToolchainStateService
import co.anbora.labs.nushell.community.ide.toolchain.Toolchain
import com.intellij.openapi.Disposable
import com.intellij.openapi.util.Condition
import java.nio.file.Path
import java.util.function.BiFunction
import java.util.function.Function

data class NewToolchainOptions(
    val undefinedVersion: String,
    val title: String,
    val toolchainFilter: Condition<Path>,
    val parentDisposable: Disposable,
    val suggestedHomePaths: List<Path>,
    val knownToolchainStateService: KnownToolchainStateService,
    val executableToolchain: ExecutableToolchain,
    val toolchainMapper: Function<String, Toolchain>,
    val versionMapper: BiFunction<String, ExecutableToolchain, String>
)
