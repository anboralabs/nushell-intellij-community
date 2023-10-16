package co.anbora.labs.nushell.community.lang.core

import co.anbora.labs.nushell.community.lang.NuShellLanguage
import co.anbora.labs.nushell.community.lang.NuShellParserDefinition.Companion.EOL_COMMENT
import co.anbora.labs.nushell.community.lang.NuShellParserDefinition.Companion.EOL_DOC_COMMENT
import co.anbora.labs.nushell.community.lang.core.psi.NuShellTypes.*
import com.intellij.psi.tree.IElementType
import com.intellij.psi.tree.TokenSet

class NuShellTokenType(debugName: String) : IElementType(debugName, NuShellLanguage)

fun tokenSetOf(vararg tokens: IElementType) = TokenSet.create(*tokens)

val KEYWORDS = tokenSetOf(
    DEF, BREAK, CONTINUE, ELSE, IF,
    FOR, LOOP, RETURN, TRY, WHILE, NULL
)

val VARIABLES_DEFINITION = tokenSetOf(
    MUT, CONST, LET, ALIAS
)

val OPERATORS_WORDS = tokenSetOf(
    MOD, IN, NOT_IN, NOT, AND,
    OR, XOR, BIT_OR, BIT_AND,
    BIT_XOR, BIT_SHL, BIT_SHR,
    STARTS_WITH, ENDS_WITH
)

val NU_COMMENTS = tokenSetOf(EOL_COMMENT, EOL_DOC_COMMENT)
