package daniel.bastidas.earthnasa.common.di

import daniel.bastidas.domain.getlistusecase.GetNaturalListUseCase
import daniel.bastidas.domain.gateway.NaturalEarthGateway
import daniel.bastidas.domain.gateway.NaturalEntity
import daniel.bastidas.earthnasa.feature.naturaldetail.NaturalDetailFragmentArgs
import daniel.bastidas.earthnasa.feature.naturaldetail.NaturalDetailViewModel
import daniel.bastidas.earthnasa.feature.naturallist.NaturalListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {
    viewModel {
        NaturalListViewModel(getNaturalListUseCase = get())
    }

    viewModel {(args: NaturalDetailFragmentArgs) -> NaturalDetailViewModel(args.naturalItem) }
}

val domainModule = module {
    factory { GetNaturalListUseCase(naturalEarthGateway = get()) }
}

val dataModule = module {
    factory <NaturalEarthGateway> { NaturalListSuccess() }
}

val koinModules = listOf(
    dataModule,
    domainModule,
    presentationModule
)


// FIXME: Delete Temp Classes
class NaturalListSuccess: NaturalEarthGateway {
    override suspend fun getNaturalList(): List<NaturalEntity>
            = listOf(
        NaturalEntity(
            "1",
            "2019-06-27 01:09:09",
            "This is earth south",
            "epic_1b_20190627011358",
            10.222,
            9.0000
        ),
        NaturalEntity(
            "2",
            "2019-06-27 01:09:09",
            "This is earth north",
            "epic_1b_20190627011358",
            10.222,
            9.0000
        ),
        NaturalEntity(
            "3",
            "2019-06-27 01:09:09",
            "This is earth east",
            "epic_1b_20190627011358",
            10.222,
            9.0000
        ),
        NaturalEntity(
            "4",
            "2019-06-27 01:09:09",
            "This is earth west",
            "epic_1b_20190627011358",
            10.222,
            9.0000
        )
    )
}
class NaturalListError: NaturalEarthGateway {
    override suspend fun getNaturalList(): List<NaturalEntity> {
        throw IllegalStateException("This is an error")
    }
}
