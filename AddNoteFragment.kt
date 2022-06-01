package com.example.mynotes


import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.fragment_add_note.*
import kotlinx.coroutines.launch
import com.example.mynotes.R
import com.example.mynotes.Note
import com.example.mynotes.NoteDatabase


class AddNoteFragment : BaseFragment() {

    private var note: Note? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        setHasOptionsMenu(true)
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_note, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        arguments?.let {
            note = AddNoteFragmentArgs.fromBundle(it).note
            EditText_Title.setText(note?.title)
            EditText_note.setText(note?.note)
        }

        button_save.setOnClickListener { view ->

            val noteTitle = EditText_Title.text.toString().trim()
            val noteBody = EditText_note.text.toString().trim()

            if (noteTitle.isEmpty()) {
                EditText_Title.error = "title required"
                EditText_Title.requestFocus()
                return@setOnClickListener
            }

            if (noteBody.isEmpty()) {
                EditText_note.error = "note required"
                EditText_note.requestFocus()
                return@setOnClickListener
            }

            launch {

                context?.let {
                    val mNote = Note(noteTitle, noteBody)

                    if (note == null) {
                        NoteDatabase(it).getNoteDao().addNote(mNote)
                        it.toast("Note Saved")
                    } else {
                        mNote.id = note!!.id
                        NoteDatabase(it).getNoteDao().updateNote(mNote)
                        it.toast("Note Updated")
                    }


                    val action = AddNoteFragmentDirections.actionSaveNote()
                    Navigation.findNavController(view).navigate(action)
                }
            }

        }

    }

    private fun deleteNote() {
        AlertDialog.Builder(context).apply {
            setTitle("Are you sure?")
            setMessage("You cannot undo this operation")
            setPositiveButton("Yes") { _, _ -> // this is syntax of lambda function
                launch {
                    NoteDatabase(context).getNoteDao().deleteNote(note!!)
                    val action = AddNoteFragmentDirections.actionSaveNote()
                    Navigation.findNavController(requireView()).navigate(action)
                }
            }
            setNegativeButton("No") { _, _ ->

            }
        }.create().show()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.delete -> if (note != null) deleteNote() else context?.toast("Cannot Delete")
        }
        return super.onOptionsItemSelected(item)
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu, menu)
    }
}

