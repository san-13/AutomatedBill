package com.example.automatedbill

import android.app.Application
import com.example.automatedbill.room.ItemRoomDatabase

class InventoryApplication:Application() {
    val database:ItemRoomDatabase by lazy { ItemRoomDatabase.getDatabase(this)}
}