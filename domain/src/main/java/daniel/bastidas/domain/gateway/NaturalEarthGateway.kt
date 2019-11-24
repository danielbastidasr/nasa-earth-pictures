package daniel.bastidas.domain.gateway

interface NaturalEarthGateway {
    suspend fun getNaturalList(): List<NaturalEntity>
}