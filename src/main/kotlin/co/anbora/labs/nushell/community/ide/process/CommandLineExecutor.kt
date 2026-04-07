package co.anbora.labs.nushell.community.ide.process

import com.intellij.execution.ExecutionException
import com.intellij.execution.configurations.GeneralCommandLine
import com.intellij.execution.process.CapturingProcessAdapter
import com.intellij.execution.process.OSProcessHandler
import com.intellij.execution.process.ProcessEvent
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.diagnostic.logger
import java.nio.charset.StandardCharsets
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

object CommandLineExecutor {
    private val LOG = logger<CommandLineExecutor>()

    fun executeAndReturnOutput(execBinName: String, exePath: String, vararg arguments: String): String {
        val cmd = GeneralCommandLine()
            .withExePath(exePath)
            .withParameters(*arguments)
            .withCharset(StandardCharsets.UTF_8)

        val processOutput = StringBuilder()
        try {
            val handler = OSProcessHandler(cmd)
            handler.addProcessListener(object : CapturingProcessAdapter() {
                override fun processTerminated(event: ProcessEvent) {
                    if (event.exitCode != 0) {
                        LOG.warn("Couldn't get $execBinName toolchain version: " + output.stderr)
                    } else {
                        processOutput.append(output.stdout)
                    }
                }
            })
            handler.startNotify()
            val future = ApplicationManager.getApplication().executeOnPooledThread {
                handler.waitFor()
            }
            future.get(2000, TimeUnit.MILLISECONDS)
        } catch (e: ExecutionException) {
            LOG.warn("Can't execute command for getting $execBinName toolchain version", e)
        } catch (e: TimeoutException) {
            LOG.warn("Can't execute command for getting $execBinName toolchain version", e)
        }
        return processOutput.toString()
    }
}