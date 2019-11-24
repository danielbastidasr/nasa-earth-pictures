package daniel.bastidas.earthnasa.common.di

import daniel.bastidas.data.NaturalEarthRepository
import daniel.bastidas.data.database.NaturalRoomDatabase
import daniel.bastidas.data.networking.JsonPlaceHolderService
import daniel.bastidas.data.networking.webserviceUrl
import daniel.bastidas.domain.getlistusecase.GetNaturalListUseCase
import daniel.bastidas.domain.gateway.NaturalEarthGateway
import daniel.bastidas.domain.listenrefreshdatausecase.ListenRefreshDataUseCase
import daniel.bastidas.earthnasa.feature.naturaldetail.NaturalDetailFragmentArgs
import daniel.bastidas.earthnasa.feature.naturaldetail.NaturalDetailViewModel
import daniel.bastidas.earthnasa.feature.naturallist.NaturalListViewModel
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

val presentationModule = module {
    viewModel {
        NaturalListViewModel(getNaturalListUseCase = get(),listenRefreshDataUseCase = get())
    }

    viewModel {(args: NaturalDetailFragmentArgs) -> NaturalDetailViewModel(args.naturalItem) }
}

val domainModule = module {
    factory { GetNaturalListUseCase(naturalEarthGateway = get()) }
    factory { ListenRefreshDataUseCase(naturalEarthGateway = get()) }
}


val dataModule = module {
    factory <NaturalEarthGateway> { NaturalEarthRepository(jsonPlaceHolderService = get(),naturalDao = get() ) }

    // Network
    single {
        OkHttpClient.Builder()
            .callTimeout(15, TimeUnit.SECONDS)
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .build()
    }
    single {
        Retrofit.Builder()
            .client(get())
            .baseUrl(webserviceUrl)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }
    single<JsonPlaceHolderService> { get<Retrofit>().create(JsonPlaceHolderService::class.java) }

    // Database
    single {
        NaturalRoomDatabase.getDatabase(context = androidApplication())
    }

    single{
        get<NaturalRoomDatabase>().naturalDao()
    }

}

val koinModules = listOf(
    dataModule,
    domainModule,
    presentationModule
)