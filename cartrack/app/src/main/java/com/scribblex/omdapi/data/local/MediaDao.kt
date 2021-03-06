package com.scribblex.omdapi.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.scribblex.omdapi.data.entities.MediaContent
import com.scribblex.omdapi.data.entities.SearchContent

@Dao
interface MediaDao {

    @Query("SELECT * FROM SearchContent")
    fun getAllMedia(): LiveData<List<SearchContent>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(media: List<SearchContent>)

    @Query("SELECT * FROM MediaContent WHERE imdbID = :id")
    fun getMedia(id: String): LiveData<MediaContent>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(media: MediaContent)

    @Query("DELETE FROM SearchContent")
    fun deleteAllSearchContent()

    @Query("DELETE FROM MediaContent")
    fun deleteAllMediaContent()
}