package co.anbora.labs.nushell.community.ide.color

import co.anbora.labs.nushell.community.ide.highlight.NuSyntaxHighlighter
import co.anbora.labs.nushell.community.ide.icons.NuIcons
import co.anbora.labs.nushell.community.lang.NuShellLanguage
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.fileTypes.SyntaxHighlighter
import com.intellij.openapi.options.colors.AttributesDescriptor
import com.intellij.openapi.options.colors.ColorDescriptor
import com.intellij.openapi.options.colors.ColorSettingsPage
import javax.swing.Icon

class NuShellColorSettingPage: ColorSettingsPage {

    private val ATTRS = NuShellColors.values().map { it.attributesDescriptor }.toTypedArray()

    override fun getAttributeDescriptors(): Array<AttributesDescriptor> = ATTRS

    override fun getColorDescriptors(): Array<ColorDescriptor> = ColorDescriptor.EMPTY_ARRAY

    override fun getDisplayName(): String = NuShellLanguage.LANGUAGE_NAME

    override fun getIcon(): Icon = NuIcons.FILE

    override fun getHighlighter(): SyntaxHighlighter = NuSyntaxHighlighter()

    override fun getDemoText(): String = NuShellLanguage.LANGUAGE_DEMO_TEXT

    override fun getAdditionalHighlightingTagToDescriptorMap(): MutableMap<String, TextAttributesKey> = mutableMapOf()
}