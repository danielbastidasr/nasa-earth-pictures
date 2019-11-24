package daniel.bastidas.domain.getlistusecase

import daniel.bastidas.domain.gateway.NaturalEarthGateway

class GetNaturalListUseCase(
    private val naturalEarthGateway: NaturalEarthGateway
) {

    suspend fun invoke(): GetListResult =
        try {
            GetListResult.GetListSuccess(naturalEarthGateway.getNaturalList())
        } catch (e: Exception) {
            GetListResult.GetListError(e)
        }
}