package co.anbora.labs.nushell.community.ide.toolchain

interface KnownToolchainStateService {
    fun knownToolchains(): Set<Toolchain>

    fun isKnown(homePath: String): Boolean

    fun add(toolchain: Toolchain)

    fun remove(toolchain: Toolchain)
}