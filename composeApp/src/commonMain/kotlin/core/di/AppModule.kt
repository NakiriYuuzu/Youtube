package core.di

import com.russhwolf.settings.Settings
import core.data.repository.YoutubeRepositoryImpl
import core.data.source.local.YoutubeLocalSource
import core.data.source.remote.YoutubeRemoteSource
import core.data.source.remote.YoutubeService
import core.domain.repository.YoutubeRepository
import core.logging.KermitLogger
import core.logging.Logger
import core.util.getDispatcherProvider
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
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
    factoryOf(::YoutubeService)
}

private val repositoryModule = module {
    singleOf(::YoutubeRepositoryImpl).bind(YoutubeRepository::class)
    factoryOf(::YoutubeLocalSource)
    factoryOf(::YoutubeRemoteSource)
}

private val viewModelModule = module {
    // viewModelOf()
}

private val appModules = listOf(
    logModule,
    utilityModule,
    localModule,
    networkModule
)

fun initKoin(appDeclaration: KoinAppDeclaration) = startKoin {
    modules(appModules)
    appDeclaration()
}

fun initKoin() = initKoin {  }
