package com.example.finalproject.repos

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import kotlin.random.Random

@Entity(tableName = "item_table")
class DBItem : Serializable{
    @PrimaryKey(autoGenerate = true)
    var id = 0

    @ColumnInfo(name = "name")
    var text_main: String = "Banana"

    @ColumnInfo(name = "fixedText")
    var text_2: String = "amount = "

    @ColumnInfo(name = "amount")
    var item_value: Int = Random.nextInt(1, 8)

    @ColumnInfo(name = "rating")
    var item_rating: Float = Random.nextFloat() * 5

    @ColumnInfo(name = "type")
    var item_type : Int = Random.nextInt(0,5)

    @ColumnInfo(name = "boolean")
    var item_checked : Boolean = Random.nextBoolean()

    constructor()

    constructor(num1 : Int,  bool : Boolean, num2 : Int, num3: Float) : this() {
        item_value = num1
        item_type = num2
        item_checked = bool
        item_rating = num3
    }

    override fun equals(other: Any?): Boolean {
        if (other == null){
            return false
        }
        return super.equals(other)
    }

}