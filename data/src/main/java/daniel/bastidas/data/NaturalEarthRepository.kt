package daniel.bastidas.data

import android.accounts.NetworkErrorException
import daniel.bastidas.data.networking.JsonPlaceHolderService
import daniel.bastidas.domain.gateway.NaturalEarthGateway
import daniel.bastidas.domain.gateway.NaturalEntity

class NaturalEarthRepository(
    private val jsonPlaceHolderService: JsonPlaceHolderService
) : NaturalEarthGateway{
    override suspend fun getNaturalList(): List<NaturalEntity> {
        val response = jsonPlaceHolderService.getNaturalList()

        if (response.isSuccessful) {
            response.body()?.let {naturalList ->

                return naturalList.map {
                    NaturalEntity(
                        id = it.id,
                        date = it.date,
                        caption = it.caption,
                        imgUrl = it.image,
                        lat = it.coordinates.lat,
                        long = it.coordinates.lon
                        )
                }

            } ?: throw NetworkErrorException("No body")
        } else {
            throw NetworkErrorException("Network Error")
        }
    }
}