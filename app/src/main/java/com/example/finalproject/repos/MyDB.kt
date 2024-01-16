package com.example.finalproject.repos

import android.content.Context
import androidx.room.Database
import androidx.room.Room.databaseBuilder
import androidx.room.RoomDatabase

@Database(entities = [DBItem::class], version = 5)
abstract class MyDB: RoomDatabase() {
    abstract fun myDao(): MyDao?

    companion object {
        private var DB_INSTANCE: MyDB? = null

        @Synchronized
        fun getDatabase(context: Context): MyDB? {
            if (DB_INSTANCE == null) {
                DB_INSTANCE = databaseBuilder(context.applicationContext,
                                            MyDB::class.java,
                                            "item_database")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return DB_INSTANCE
        }
    }
}