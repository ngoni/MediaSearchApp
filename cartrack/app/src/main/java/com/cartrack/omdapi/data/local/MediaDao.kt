package com.cartrack.omdapi.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.cartrack.omdapi.data.entities.MediaContent
import com.cartrack.omdapi.data.entities.Search

@Dao
interface MediaDao {

    @Query("SELECT * FROM SearchContent")
    fun getAllMedia() : LiveData<List<Search>>

    @Query("SELECT * FROM SearchContent WHERE imdbID = :id")
    fun getMedia(id: String) : LiveData<Search>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(media: List<Search>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(media: MediaContent)

}