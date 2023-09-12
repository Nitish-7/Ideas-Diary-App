package com.app.diary.ui.adapters

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.app.diary.models.NoteResponse

@BindingAdapter("items")
fun setRecyclerViewItemsI(recyclerView: RecyclerView, items: List<NoteResponse>?) {
    val adapter = recyclerView.adapter as? NoteAdapter
        recyclerView.adapter = adapter
    adapter?.submitList(items)
}
