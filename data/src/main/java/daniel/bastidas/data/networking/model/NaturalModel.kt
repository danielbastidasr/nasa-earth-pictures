package daniel.bastidas.data.networking.model

import com.squareup.moshi.Json

data class NaturalModel (
    @field:Json(name = "identifier") val id:String,
    @field:Json(name = "caption") val caption:String,
    @field:Json(name = "image") val image:String,
    @field:Json(name = "centroid_coordinates") val coordinates: CoordinateModel,
    @field:Json(name = "date") val date:String
)
