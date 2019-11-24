package daniel.bastidas.domain.getlistusecase

sealed class GetListResult {
    object GetListSuccess: GetListResult()

    data class GetListError(
        val exception: Exception
    ) : GetListResult()
}
