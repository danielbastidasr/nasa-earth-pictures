package daniel.bastidas.earthnasa.feature.naturaldetail.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Coordinates(
    val lat:Double,
    val long:Double
):Parcelable