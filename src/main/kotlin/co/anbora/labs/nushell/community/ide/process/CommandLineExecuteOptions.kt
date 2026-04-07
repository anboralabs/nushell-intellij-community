package co.anbora.labs.nushell.community.ide.process

data class CommandLineExecuteOptions(
    val execBinName: String,
    val exePath: String,
    val arguments: List<String>
)
