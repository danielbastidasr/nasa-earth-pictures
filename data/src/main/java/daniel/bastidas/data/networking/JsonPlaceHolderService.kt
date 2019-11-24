package daniel.bastidas.data.networking

import daniel.bastidas.data.model.NaturalModel
import retrofit2.Response
import retrofit2.http.GET

interface JsonPlaceHolderService {

    @GET("natural")
    suspend fun getNaturalList(): Response<List<NaturalModel>>

}