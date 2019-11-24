package daniel.bastidas.earthnasa.common.aux

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import daniel.bastidas.domain.gateway.NaturalEntity
import daniel.bastidas.earthnasa.feature.naturaldetail.model.Coordinates
import daniel.bastidas.earthnasa.feature.naturaldetail.model.NaturalEarth

fun <T> LifecycleOwner.observe(liveData: LiveData<T>, body: (T) -> Unit = {}) {
    liveData.observe(this, Observer { it?.let { t -> body(t) } })
}

fun NaturalEntity.toPresentation(): NaturalEarth =
    NaturalEarth(
        id = id,
        date = date,
        caption = caption,
        image = imgUrl,
        coordinates = getCoordinates()
    )

fun NaturalEntity.getCoordinates():Coordinates =
    Coordinates(lat = lat,long = long)

fun Exception.toPresentation() = "$message"

fun NaturalEarth.getUrl(imageType: ImageType):String{
    val baseUrl = "https://epic.gsfc.nasa.gov/archive/natural"
    val dateParse = date
        .substringBefore(' ')
        .replace('-','/')

    return "$baseUrl/$dateParse/${imageType.name}/$image.jpg"
}


