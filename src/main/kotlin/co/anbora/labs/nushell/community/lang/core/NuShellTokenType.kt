package co.anbora.labs.nushell.community.lang.core

import co.anbora.labs.nushell.community.lang.NuShellLanguage
import com.intellij.psi.tree.IElementType
import com.intellij.psi.tree.TokenSet

class NuShellTokenType(debugName: String) : IElementType(debugName, NuShellLanguage)

fun tokenSetOf(vararg tokens: IElementType) = TokenSet.create(*tokens)

/*val KEYWORDS = tokenSetOf(
    TAP, CASK_ARGS, BREW, CASK,
    UNLESS, MAS, WHALEBREW
)*/

/*val RULES_PERMISSIONS = tokenSetOf(
        CREATE, DELETE, EXITS, GET, READ, UPDATE, WRITE, LIST
)

val TYPES = tokenSetOf(
        BOOL, INT, FLOAT, NUMBER, STRING, LIST, MAP, TIMESTAMP, DURATION, PATH, LATLNG
)*/

//val BREW_COMMENTS = tokenSetOf(EOL_COMMENT, EOL_DOC_COMMENT)
