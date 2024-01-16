package com.example.finalproject.repos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface MyDao {
    @Query("SELECT * FROM item_table ORDER BY id ASC")
    fun getAllData(): Flow<MutableList<DBItem>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(item: DBItem?) : Long

    @Delete
    fun delete(item: DBItem?) : Int

    @Query("DELETE FROM item_table")
    fun deleteAll()

    @Update(onConflict = OnConflictStrategy.IGNORE)
    fun update(item: DBItem): Unit
}