package sky.sanjay.notedown

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.text.SimpleDateFormat
import java.util.*

class AddEditNoteActivity : AppCompatActivity() {

    lateinit var notesTitleET: EditText
    lateinit var notesDescriptionET: EditText
    lateinit var updateButton: Button
    lateinit var viewModel: NoteViewModel
    var noteId = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit_note)

        notesTitleET = findViewById(R.id.noteTitleET)
        notesDescriptionET = findViewById(R.id.noteDescriptionET)
        updateButton = findViewById(R.id.noteUpdateButton)

        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(NoteViewModel::class.java)

        val noteType = intent.getStringExtra("noteType")
        if(noteType.equals("Edit")){
            val noteTitle = intent.getStringExtra("noteTitle")
            val noteDesc = intent.getStringExtra("noteDescription")
            noteId = intent.getIntExtra("noteID", -1)
            updateButton.setText("Update Note")
            notesTitleET.setText(noteTitle)
            notesDescriptionET.setText(noteDesc)
        } else {
            updateButton.setText("Save Note")
        }

        updateButton.setOnClickListener{
            val noteTitle = notesTitleET.text.toString()
            val noteDesc =  notesDescriptionET.text.toString()

            if(noteType.equals("Edit")){
                if(noteTitle.isNotEmpty() && noteDesc.isNotEmpty()){
                    val sdf = SimpleDateFormat("dd MM, yyyy - HH:mm")
                    val currentDate: String = sdf.format(Date())
                    val updateNote = Note(noteTitle,noteDesc,currentDate)
                    updateNote.id = noteId
                    viewModel.updateNote(updateNote)
                    Toast.makeText(this, "Note Updated", Toast.LENGTH_LONG).show()
                }
            } else {
                if(noteTitle.isNotEmpty() && noteDesc.isNotEmpty()){
                    val sdf = SimpleDateFormat("dd MM, yyyy - HH:mm")
                    val currentDate: String = sdf.format(Date())
                    viewModel.insertNote(Note(noteTitle,noteDesc,currentDate))
                    Toast.makeText(this, "Note Added", Toast.LENGTH_LONG).show()
                }
            }
            startActivity(Intent(applicationContext, MainActivity::class.java))
            this.finish()
        }
    }
}