fun properties(key: String) = providers.gradleProperty(key)
fun environment(key: String) = providers.environmentVariable(key)

plugins {
    id("java")
    id("org.jetbrains.kotlin.jvm") version "2.0.0"
    id("org.jetbrains.intellij.platform") version "2.0.0-beta7"
    id("org.jetbrains.grammarkit") version "2022.3.2.2"
}

group = properties("pluginGroup").get()
version = properties("pluginVersion").get()

// Set the JVM language level used to build the project.
kotlin {
    jvmToolchain(17)
}

// Configure project's dependencies
repositories {
    mavenCentral()

    // IntelliJ Platform Gradle Plugin Repositories Extension - read more: https://plugins.jetbrains.com/docs/intellij/tools-intellij-platform-gradle-plugin-repositories-extension.html
    intellijPlatform {
        defaultRepositories()
    }
}

dependencies {
    // IntelliJ Platform Gradle Plugin Dependencies Extension - read more: https://plugins.jetbrains.com/docs/intellij/tools-intellij-platform-gradle-plugin-dependencies-extension.html
    intellijPlatform {
        create(properties("platformType"), properties("platformVersion"))

        // Plugin Dependencies. Uses `platformBundledPlugins` property from the gradle.properties file for bundled IntelliJ Platform plugins.
        bundledPlugins(properties("platformBundledPlugins").map { it.split(',') })

        // Plugin Dependencies. Uses `platformPlugins` property from the gradle.properties file for plugin from JetBrains Marketplace.
        plugins(properties("platformPlugins").map { it.split(',') })

        instrumentationTools()
        pluginVerifier()
        // testFramework(TestFrameworkType.Platform.JUnit4)
    }
}

sourceSets {
    main {
        java.srcDirs("src/main/gen")
    }
}

// Configure IntelliJ Platform Gradle Plugin - read more: https://plugins.jetbrains.com/docs/intellij/tools-intellij-platform-gradle-plugin-extension.html
intellijPlatform {
    pluginConfiguration {
        version = properties("pluginVersion")
        description = file("src/main/html/description.html").inputStream().readBytes().toString(Charsets.UTF_8)
        changeNotes = file("src/main/html/change-notes.html").inputStream().readBytes().toString(Charsets.UTF_8)

        ideaVersion {
            sinceBuild = properties("pluginSinceBuild")
            untilBuild = properties("pluginUntilBuild")
        }
    }

    publishing {
        token = environment("PUBLISH_TOKEN")
        // The pluginVersion is based on the SemVer (https://semver.org) and supports pre-release labels, like 2.1.7-alpha.3
        // Specify pre-release label to publish the plugin in a custom Release Channel automatically. Read more:
        // https://plugins.jetbrains.com/docs/intellij/deployment.html#specifying-a-release-channel
        channels = properties("pluginVersion").map { listOf(it.substringAfter('-', "").substringBefore('.').ifEmpty { "default" }) }
    }

    verifyPlugin {
        ides {
            recommended()
        }
    }
}

tasks {

    wrapper {
        gradleVersion = properties("gradleVersion").get()
    }

    val generateNuShellLexer = task<org.jetbrains.grammarkit.tasks.GenerateLexerTask>("generateNuShellLexer") {
        sourceFile.set(file("src/main/grammar/NuShell.flex"))
        targetOutputDir.set(file("src/main/gen/co/anbora/labs/nushell/community/lang/"))
        purgeOldFiles.set(true)
    }

    val generateNuShellParser = task<org.jetbrains.grammarkit.tasks.GenerateParserTask>("generateNuShellParser") {
        sourceFile.set(file("src/main/grammar/NuShell.bnf"))
        targetRootOutputDir.set(file("src/main/gen"))
        pathToParser.set("/co/anbora/labs/nushell/community/lang/core/parser/NuShellParser.java")
        pathToPsiRoot.set("/co/anbora/labs/nushell/community/lang/core/psi")
        purgeOldFiles.set(true)
    }

    publishPlugin {
        dependsOn(generateNuShellLexer, generateNuShellParser)
    }
}
