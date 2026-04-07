package co.anbora.labs.nushell.community.ide.toolchain

import java.nio.file.Path

interface Toolchain {
    fun name(): String
    fun version(): String
    fun stdBinDir(): Path?
    fun rootDir(): Path?
    fun homePath(): String
    fun isValid(): Boolean
}