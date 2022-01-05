package com.cartrack.omdapi.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.cartrack.omdapi.data.entities.MediaContent

@Dao
interface MediaDao {

    @Query("SELECT * FROM MediaContent")
    fun getAllMedia() : LiveData<List<MediaContent>>

    @Query("SELECT * FROM MediaContent WHERE imdbID = :id")
    fun getMedia(id: String) : LiveData<MediaContent>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(media: List<MediaContent>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(media: MediaContent)

}