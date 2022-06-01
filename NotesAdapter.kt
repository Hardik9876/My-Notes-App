package com.example.mynotes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.note_layout.view.*

class NotesAdapter(private val notes:List<Note>):RecyclerView.Adapter<NotesAdapter.NoteViewHolder>()
{
    class NoteViewHolder( val view:View):RecyclerView.ViewHolder(view)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.note_layout,parent,false)
        )
    }

    override fun getItemCount(): Int {
       return notes.size
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
       holder.view.textView_title.text=notes[position].title
       holder.view.textView_note.text=notes[position].note
        holder.view.setOnClickListener{
            val action=HomeFragmentDirections.actionAddNote()
            action.note=notes[position]
            Navigation.findNavController(it).navigate(action)
        }
    }
}