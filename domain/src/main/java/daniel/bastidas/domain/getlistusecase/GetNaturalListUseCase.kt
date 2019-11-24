package daniel.bastidas.domain.getlistusecase

import daniel.bastidas.domain.gateway.NaturalEarthGateway

class GetNaturalListUseCase(
    private val naturalEarthGateway: NaturalEarthGateway
) {

    companion object{
        const val NETWORK_ERROR = "Network Error"
    }

    suspend fun invoke(): GetListResult =
         try {
            if(naturalEarthGateway.getNaturalList()){
                 GetListResult.GetListSuccess
            }
            else{
                 GetListResult.GetListError(Exception(NETWORK_ERROR))
            }

        } catch (e: Exception) {
            GetListResult.GetListError(e)
        }
}