package daniel.bastidas.domain.listenrefreshdatausecase

import daniel.bastidas.domain.gateway.NaturalEarthGateway
import daniel.bastidas.domain.gateway.NaturalEntity
import kotlinx.coroutines.flow.Flow

class ListenRefreshDataUseCase(
    private val naturalEarthGateway: NaturalEarthGateway
) {

    fun invoke():Flow<List<NaturalEntity>> {
       return naturalEarthGateway.listenDataList()
    }
}