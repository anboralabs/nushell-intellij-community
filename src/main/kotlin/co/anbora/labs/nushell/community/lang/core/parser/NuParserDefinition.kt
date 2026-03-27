package co.anbora.labs.nushell.community.lang.core.parser

import com.intellij.lang.ParserDefinition
import com.intellij.openapi.extensions.ExtensionPointName

interface NuParserDefinition : ParserDefinition {
    val order: Int

    companion object {
        val EP_NAME: ExtensionPointName<NuParserDefinition> = ExtensionPointName.create("co.anbora.labs.nushell.community.parserDefinition")
    }
}
