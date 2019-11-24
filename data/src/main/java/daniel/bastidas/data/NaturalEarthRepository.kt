package daniel.bastidas.data

import daniel.bastidas.data.database.NaturalDao
import daniel.bastidas.data.database.NaturalModelDB
import daniel.bastidas.data.networking.JsonPlaceHolderService
import daniel.bastidas.domain.gateway.NaturalEarthGateway
import daniel.bastidas.domain.gateway.NaturalEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.lang.Exception

class NaturalEarthRepository(
    private val jsonPlaceHolderService: JsonPlaceHolderService,
    private val naturalDao:NaturalDao
) : NaturalEarthGateway{

    override suspend fun getNaturalList():Boolean =
        try {
            val response = jsonPlaceHolderService.getNaturalList()
            if (response.isSuccessful) {
                response.body()?.let {naturalList ->

                    naturalDao.insert(
                        naturalList.map {
                            NaturalModelDB(
                                id = it.id,
                                date = it.date,
                                caption = it.caption,
                                image = it.image,
                                lat = it.coordinates.lat,
                                lon = it.coordinates.lon
                            )
                        }
                    )
                    return true

                } ?: false
            } else {
                false
            }
        }
        catch (error:Exception){
            false
        }


    override fun listenDataList(): Flow<List<NaturalEntity>> =
        naturalDao.getNaturalPictures().map {
            it.map {
                NaturalEntity(
                    id = it.id,
                    date = it.date,
                    caption = it.caption,
                    imgUrl = it.image,
                    lat = it.lat,
                    long = it.lon
                )
            }
        }
}