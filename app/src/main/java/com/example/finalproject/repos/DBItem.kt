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
    var name: String = "Banana"

    @ColumnInfo(name = "amount")
    var number: Int = Random.nextInt(1, 8)

    @ColumnInfo(name = "rating")
    var rating: Float = Random.nextFloat() * 5

    @ColumnInfo(name = "boolean")
    var inBasket : Boolean = Random.nextBoolean()

    constructor()

    constructor(name : String,  number : Int, rating: Float, inBasket: Boolean) : this() {
        this.name = name
        this.number = number
        this.rating = rating
        this.inBasket = inBasket
    }

    override fun equals(other: Any?): Boolean {
        if (other == null){
            return false
        }
        return super.equals(other)
    }

}