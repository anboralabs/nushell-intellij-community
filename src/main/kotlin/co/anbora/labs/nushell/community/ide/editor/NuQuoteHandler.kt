package co.anbora.labs.nushell.community.ide.editor

import co.anbora.labs.nushell.community.lang.core.psi.NuShellTypes.DOUBLE_QUOTE
import co.anbora.labs.nushell.community.lang.core.psi.NuShellTypes.SINGLE_QUOTE
import com.intellij.codeInsight.editorActions.SimpleTokenSetQuoteHandler


class NuQuoteHandler: SimpleTokenSetQuoteHandler(SINGLE_QUOTE, DOUBLE_QUOTE)