package co.anbora.labs.nushell.community.ide.toolchain

abstract class NullToolchain(
    protected val executable: String
): Toolchain {
    override fun name() = "<No $executable shell>"

    override fun version() = ""

    override fun stdBinDir() = null

    override fun rootDir() = null

    override fun homePath() = ""

    override fun isValid() = false
}