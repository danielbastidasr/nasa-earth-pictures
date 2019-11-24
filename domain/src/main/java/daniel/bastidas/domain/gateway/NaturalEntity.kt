package daniel.bastidas.domain.gateway

data class NaturalEntity (
    val id:String,
    val date:String,
    val caption:String,
    val imgUrl:String,
    val lat:Double,
    val long:Double
)
