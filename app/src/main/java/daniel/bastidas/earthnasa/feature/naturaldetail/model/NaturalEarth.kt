package daniel.bastidas.earthnasa.feature.naturaldetail.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class NaturalEarth (
    val id:String,
    val date:String,
    val caption:String,
    val image:String,
    val coordinates: Coordinates
):Parcelable