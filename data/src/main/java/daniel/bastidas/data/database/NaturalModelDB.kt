package daniel.bastidas.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "natural_table")
data class NaturalModelDB (
    @PrimaryKey val id:String,
    @ColumnInfo(name = "caption") val caption:String,
    @ColumnInfo(name = "image") val image:String,
    @ColumnInfo(name = "lat") val lat: Double,
    @ColumnInfo(name = "lon") val lon: Double,
    @ColumnInfo(name = "date") val date:String
)
