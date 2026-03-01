package co.anbora.labs.nushell.community.lang

/**
 * Holds lexer-level token constants used by the generated NuShellLexer.
 * The actual ParserDefinition (PSI layer) lives in the Pro plugin.
 */
class NuShellParserDefinition {

    companion object {
        @JvmField val LINE_TERMINATOR = co.anbora.labs.nushell.community.lang.core.psi.NuShellTypes.LINE_TERMINATOR
    }
}
