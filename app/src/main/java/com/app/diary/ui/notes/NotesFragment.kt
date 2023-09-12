package com.app.diary.ui.notes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.app.diary.R
import com.app.diary.databinding.FragmentNotesBinding
import com.app.diary.models.NoteResponse
import com.app.diary.ui.adapters.NoteAdapter
import com.app.diary.util.ApiResultHandler
import com.app.diary.util.Constants
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotesFragment : Fragment() {
    private var _binding: FragmentNotesBinding? = null
    private val binding get() = _binding!!
    private val notesViewModel by viewModels<NotesViewModel>()

    //@Inject
    lateinit var noteAdapter: NoteAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNotesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.notesViewModel = notesViewModel
        binding.lifecycleOwner = viewLifecycleOwner
        binding.progressBar.isVisible = true
        noteAdapter = NoteAdapter(onNoteItemClicked)
        binding.noteList.run {
            adapter = noteAdapter
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        }

        bindObservers()
        handleClicks()
    }

    private fun handleClicks() {
        binding.addNote.setOnClickListener {
            findNavController().navigate(R.id.action_notesFragment_to_editNoteFragment)
        }

    }

    private fun bindObservers() {
        notesViewModel.noteData.observe(viewLifecycleOwner, Observer {
            binding.progressBar.isVisible = false
            noteAdapter.submitList(it)
        })
    }

    private val onNoteItemClicked: (noteResponse: NoteResponse) -> Unit = {
        val bundle = Bundle()
        bundle.putString(Constants.BUNDLE_NOTES_TO_EDIT, Gson().toJson(it))
        findNavController().navigate(R.id.action_notesFragment_to_editNoteFragment, bundle)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}