package cn.neday.sheep.model.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import cn.neday.sheep.model.HistoryWords
import cn.neday.sheep.model.dao.HistoryWordsDao

@Database(entities = [HistoryWords::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun historyWordsDao(): HistoryWordsDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "Sheep_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}
