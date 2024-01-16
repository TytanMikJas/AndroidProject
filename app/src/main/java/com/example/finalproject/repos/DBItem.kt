package com.example.finalproject.repos

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.finalproject.R
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

    var image: Int = R.drawable.banana

    constructor()

    constructor(name : String,  number : Int, rating: Float, inBasket: Boolean) : this() {
        this.name = name
        this.number = number
        this.rating = rating
        this.inBasket = inBasket
        when (name.lowercase()) {
            "banana" -> image = R.drawable.banana
            "cherry" -> R.drawable.cherry
            "plum" -> R.drawable.plum
            "mandarin" -> R.drawable.mandarin
            "mango" -> R.drawable.mango
            "pear" -> R.drawable.pear
            "apple" -> R.drawable.apple
            "pineapple" -> R.drawable.pineapple
        }
    }

    override fun equals(other: Any?): Boolean {
        if (other == null){
            return false
        }
        return super.equals(other)
    }

}