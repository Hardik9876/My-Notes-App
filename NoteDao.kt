package com.example.mynotes

import androidx.room.*

@Dao
interface NoteDao {
   @Insert
   suspend fun addNote(note:Note)

   @Query("SELECT * FROM note ORDER BY id DESC")
   suspend fun getAllNotes():List<Note>

    @Update
    suspend fun updateNote(note:Note)

    @Delete
    suspend fun deleteNote(note: Note)

}