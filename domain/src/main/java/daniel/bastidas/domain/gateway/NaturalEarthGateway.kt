package daniel.bastidas.domain.gateway

import kotlinx.coroutines.flow.Flow

interface NaturalEarthGateway {
    suspend fun getNaturalList():Boolean
    fun listenDataList():Flow<List<NaturalEntity>>
}