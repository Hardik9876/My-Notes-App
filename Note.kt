package com.example.mynotes

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class Note(
   // @ColumnInfo(name = "note_title") [gives column name]
val title:String,val note:String) :Serializable
{
    @PrimaryKey(autoGenerate = true)
    var id:Int=0
}