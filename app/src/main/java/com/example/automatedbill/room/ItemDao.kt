package com.example.automatedbill.room

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ItemDao {
    @Insert
    suspend fun insert(item: Item)

    @Update
    suspend fun update(item: Item)

    @Delete
    suspend fun delete(item: Item)

    @Query("Select * from Item Order by id ASC")
    fun getItems(): Flow<List<Item>>

    @Query("Select * from Item WHERE id=:id")
    fun getItem(id:Int):Flow<Item>
}