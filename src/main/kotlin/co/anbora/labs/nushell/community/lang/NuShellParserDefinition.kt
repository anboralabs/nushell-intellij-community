package co.anbora.labs.nushell.community.lang

import co.anbora.labs.nushell.community.lang.core.NuShellTokenType

class NuShellParserDefinition {

    companion object {
        @JvmField val EOL_COMMENT = NuShellTokenType("EOL_COMMENT")
        @JvmField val EOL_DOC_COMMENT = NuShellTokenType("EOL_DOC_COMMENT")
    }
}