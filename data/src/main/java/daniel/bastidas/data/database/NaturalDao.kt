package daniel.bastidas.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface NaturalDao {

    @Query("SELECT * from natural_table ORDER BY date ")
    fun getNaturalPictures(): Flow<List<NaturalModelDB>>

    /*
     For this example We assume that modifications will be done in the backend
     Other Option if We know that new data just will be added, then We can use
     the strategy: OnConflictStrategy.IGNORE
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(naturalEarth: List<NaturalModelDB>)
}