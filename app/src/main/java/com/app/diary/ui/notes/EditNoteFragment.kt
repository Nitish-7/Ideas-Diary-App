package com.app.diary.ui.notes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.app.diary.R
import com.app.diary.databinding.FragmentEditNoteBinding
import com.app.diary.models.NoteRequest
import com.app.diary.models.NoteResponse
import com.app.diary.models.UserRequest
import com.app.diary.util.Constants
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditNoteFragment : Fragment() {
    private var _binding: FragmentEditNoteBinding? = null
    private val binding get() = _binding!!
    private val notesViewModel by viewModels<NotesViewModel>()
    private var note:NoteResponse?=null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindUiData()
        handleClicks()
    }

    private fun bindUiData() {
        val bundle: String? = arguments?.getString(Constants.BUNDLE_NOTES_TO_EDIT, null)
        if (bundle != null) {
            note = Gson().fromJson<NoteResponse>(bundle, NoteResponse::class.java)
            note?.let {
                binding.apply {
                    txtTitle.setText(it.title)
                    txtDescription.setText(it.description)
                }
            }
        } else {
            binding.addEditText.text = resources.getString(R.string.add_note)
        }
    }


    private fun handleClicks() {
        binding.run {
            btnSubmit.setOnClickListener {
                addNote()
                findNavController().popBackStack()
            }
            btnDelete.setOnClickListener {
                deleteNote()
                findNavController().popBackStack()
            }
        }
    }

    private fun deleteNote() {
        note?.let { notesViewModel.deleteNote(it) }
    }

    private fun addNote() {
        val noteRequest = binding.run {
            NoteRequest(txtTitle.text.toString(), txtDescription.text.toString())
        }
        if (note!=null) {
            val updatedNote=noteRequest?.run {
                NoteResponse(l_id = note!!.l_id, title = title, description = description)
            }
            notesViewModel.updateNote(updatedNote)
        } else {
            notesViewModel.createNote(noteRequest)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}