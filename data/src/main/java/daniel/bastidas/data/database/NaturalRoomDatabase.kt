package daniel.bastidas.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(NaturalModelDB::class), version = 1, exportSchema = false)
abstract class NaturalRoomDatabase : RoomDatabase() {
    abstract fun naturalDao(): NaturalDao

    companion object {
        @Volatile
        private var INSTANCE: NaturalRoomDatabase? = null

        fun getDatabase(context: Context): NaturalRoomDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NaturalRoomDatabase::class.java,
                    "natural_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}
