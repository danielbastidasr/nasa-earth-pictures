package daniel.bastidas.domain.getlistusecase

import daniel.bastidas.domain.gateway.NaturalEntity

sealed class GetListResult {
    data class GetListSuccess(
        val listEntities: List<NaturalEntity>
    ) : GetListResult()

    data class GetListError(
        val exception: Exception
    ) : GetListResult()
}
