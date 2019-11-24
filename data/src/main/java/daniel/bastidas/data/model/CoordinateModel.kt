package daniel.bastidas.data.model

import com.squareup.moshi.Json

data class CoordinateModel(
    @field:Json(name = "lat") val lat:Double,
    @field:Json(name = "lon") val lon:Double
)