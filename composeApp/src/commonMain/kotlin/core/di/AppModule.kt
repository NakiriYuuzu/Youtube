package core.di

import com.russhwolf.settings.Settings
import core.data.source.YoutubeSource
import core.data.source.local.YoutubeLocalSource
import core.data.source.remote.Api
import core.data.source.remote.YoutubeRemoteSource
import core.logging.KermitLogger
import core.logging.Logger
import core.util.getDispatcherProvider
import feature.main.MainViewModel
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.bind
import org.koin.dsl.module

private val logModule = module {
    singleOf<Logger>(::KermitLogger)
}

private val utilityModule = module {
    factory { getDispatcherProvider() }
}

private val localModule = module {
    factoryOf(::Settings)
}

private val networkModule = module {
    single { Api.initSupabase() }
}

private val repositoryModule = module {
    factoryOf(::YoutubeLocalSource).bind(YoutubeSource.Local::class)
    factoryOf(::YoutubeRemoteSource).bind(YoutubeSource.Remote::class)
}

private val viewModelModule = module {
    viewModelOf(::MainViewModel)
}

private val appModules = listOf(
    logModule,
    utilityModule,
    localModule,
    networkModule,
    repositoryModule,
    viewModelModule
)

fun initKoin(appDeclaration: KoinAppDeclaration) = startKoin {
    modules(appModules)
    appDeclaration()
}

fun initKoin() = initKoin {  }
