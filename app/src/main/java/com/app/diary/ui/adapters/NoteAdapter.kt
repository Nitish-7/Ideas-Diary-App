package com.app.diary.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.app.diary.R
import com.app.diary.databinding.NoteItemBinding
import com.app.diary.models.NoteResponse
import javax.inject.Inject

class NoteAdapter @Inject constructor(
    private val onNoteItemClicked: (noteResponse: NoteResponse) -> Unit)
    : ListAdapter<NoteResponse, NoteAdapter.NoteViewHolder>(MyComparator()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder(NoteItemBinding
            .inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
         val note=getItem(position)
         note?.run {
             holder.bindData(this)
        }
        holder.bindData(note)
    }

    class MyComparator: DiffUtil.ItemCallback<NoteResponse>() {

        override fun areItemsTheSame(oldItem: NoteResponse, newItem: NoteResponse): Boolean {
            return oldItem._id==newItem._id
        }

        override fun areContentsTheSame(oldItem: NoteResponse, newItem: NoteResponse): Boolean {
            return oldItem==newItem
        }
    }


    inner class NoteViewHolder(private val binding: NoteItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bindData(noteResponse: NoteResponse){
            binding.note=noteResponse
            binding.root.setOnClickListener {
                onNoteItemClicked(noteResponse)
            }
        }
    }

}