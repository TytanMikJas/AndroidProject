package com.example.finalproject.repos

import android.content.Context
import kotlinx.coroutines.flow.Flow


class DataRepo(context: Context) {

    private var myDao: MyDao
    private var db: MyDB

    companion object{
        private var R_INSTANCE: DataRepo? = null

        fun getInstance(context: Context): DataRepo {
            if (R_INSTANCE == null){
                R_INSTANCE = DataRepo(context)
            }
            return R_INSTANCE as DataRepo
        }
    }

    init {
        db = MyDB.getDatabase(context)!!
        myDao = db.myDao()!!
    }

    fun addItem(item: DBItem) : Boolean {
        return myDao.insert(item) >= 0
    }

    fun deleteItem(item: DBItem) : Boolean {
        return myDao.delete(item) > 0
    }

    fun modifyItem(item: DBItem) {
        return myDao.update(item)
    }

    fun getData(): Flow<MutableList<DBItem>> {
        return myDao.getAllData()
    }
}