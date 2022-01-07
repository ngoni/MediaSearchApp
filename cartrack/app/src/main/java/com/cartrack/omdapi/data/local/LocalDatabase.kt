package com.cartrack.omdapi.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.cartrack.omdapi.data.entities.MediaContent
import com.cartrack.omdapi.data.entities.SearchContent

@Database(entities = [MediaContent::class, SearchContent::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class LocalDatabase : RoomDatabase() {

    abstract fun mediaDao(): MediaDao

    companion object {
        private const val DB_FILE_NAME = "media.db"
        @Volatile
        private var instance: LocalDatabase? = null

        fun getDatabase(context: Context): LocalDatabase =
            instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context, LocalDatabase::class.java, DB_FILE_NAME)
                .fallbackToDestructiveMigration()
                .build()
    }
}