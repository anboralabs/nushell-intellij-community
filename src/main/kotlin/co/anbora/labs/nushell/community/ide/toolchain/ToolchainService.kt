package co.anbora.labs.nushell.community.ide.toolchain

interface ToolchainService {
    fun setToolchain(newToolchain: Toolchain)

    fun toolchain(): Toolchain
}