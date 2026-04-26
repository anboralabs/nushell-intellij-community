package co.anbora.labs.nushell.community.lang.lexer

import com.intellij.psi.TokenType.BAD_CHARACTER
import com.intellij.testFramework.LexerTestCase
import java.io.File

/**
 * Validates that the NuShell lexer does not produce BAD_CHARACTER tokens
 * for any .nu script in the std_nu directory.
 */
class NuLexerStdNuTest : LexerTestCase() {

    override fun createLexer() = NuLexer()

    override fun getDirPath() = "src/test/resources/std_nu"

    private fun collectNuScripts(): List<File> {
        val root = File(dirPath)
        if (!root.exists()) return emptyList()
        return root.walkTopDown().filter { it.extension == "nu" }.toList()
    }

    fun testAllStdNuScriptsLexWithoutErrors() {
        val scripts = collectNuScripts()
        assertTrue("No .nu scripts found in std_nu directory", scripts.isNotEmpty())

        val failures = mutableListOf<String>()

        for (script in scripts) {
            val text = script.readText()
            val lexer = createLexer()
            lexer.start(text)

            var badCharOffset = -1
            while (lexer.tokenType != null) {
                if (lexer.tokenType == BAD_CHARACTER) {
                    badCharOffset = lexer.tokenStart
                    break
                }
                lexer.advance()
            }

            if (badCharOffset >= 0) {
                val relativePath = script.relativeTo(File(dirPath)).path
                failures.add("$relativePath (offset=$badCharOffset)")
            }
        }

        println("Lexer validation: ${scripts.size - failures.size}/${scripts.size} scripts passed without BAD_CHARACTER")
        if (failures.isNotEmpty()) {
            println("Scripts with BAD_CHARACTER (${failures.size}):")
            failures.forEach { println("  - $it") }
        }

        assertEquals(
            "Lexer produced BAD_CHARACTER in ${failures.size}/${scripts.size} scripts:\n${failures.joinToString("\n")}",
            0,
            failures.size
        )
    }
}
