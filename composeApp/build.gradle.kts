import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpackConfig

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.gradleConfig)
    alias(libs.plugins.ksp)
}

buildConfig {
    useKotlinOutput { internalVisibility = true }
    val supabaseUrl: String? = System.getenv("SUPABASE_URL")
    val supabaseKey: String? = System.getenv("SUPABASE_KEY")
    buildConfigField("String", "SUPABASE_URL", supabaseUrl)
    buildConfigField("String", "SUPABASE_KEY", supabaseKey)
}

kotlin {
    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        moduleName = "composeApp"
        browser {
            val rootDirPath = project.rootDir.path
            val projectDirPath = project.projectDir.path
            commonWebpackConfig {
                outputFileName = "composeApp.js"
                devServer = (devServer ?: KotlinWebpackConfig.DevServer()).apply {
                    static = (static ?: mutableListOf()).apply {
                        // Serve sources to debug inside browser
                        add(rootDirPath)
                        add(projectDirPath)
                    }
                }
            }
        }
        binaries.executable()
    }

    js(IR) {
        moduleName = "composeApp"
        browser {
            commonWebpackConfig {
                outputFileName = "composeApp.js"
            }
        }
        binaries.executable()
    }
    
    sourceSets {
        commonMain.dependencies {
            implementation(compose.ui)
            implementation(compose.runtime)
            implementation(compose.material3)
            implementation(compose.foundation)
            implementation(compose.runtimeSaveable)
            implementation(compose.components.resources)
            implementation(compose.materialIconsExtended)
            implementation(compose.components.uiToolingPreview)

            implementation(libs.bundles.androidx)
            implementation(libs.bundles.kotlinx)
            implementation(libs.bundles.material)
            implementation(libs.bundles.ui)
            implementation(libs.bundles.network)
            implementation(libs.bundles.database)
            implementation(libs.bundles.di)
            implementation(libs.bundles.debug)

            implementation(libs.supabase.postgres)
        }
        jsMain.dependencies {
            implementation(libs.ktor.client.js)
        }
        wasmJsMain.dependencies {
            implementation(libs.ktor.client.wasmjs)
        }
//        val wasmJsMain by getting {
//            dependencies {
//            }
//        }
    }

    jvmToolchain(17)
}


