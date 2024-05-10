package com.autthapol.recyclerviewsampleapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.checkbox.MaterialCheckBox

class ItemListAdapter(private val onItemClicked: (Item) -> Unit) :
    ListAdapter<Item, ItemViewHolder>(ItemDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        return ItemViewHolder(view, onItemClicked)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }
}

class ItemViewHolder(itemView: View, private val onItemClicked: (Item) -> Unit) :
    RecyclerView.ViewHolder(itemView) {
    fun bind(item: Item) {
        val tvName = itemView.findViewById<TextView>(R.id.tv_name)
        val checkbox = itemView.findViewById<MaterialCheckBox>(R.id.checkbox)

        checkbox.setOnCheckedChangeListener { _, isChecked -> item.checked = isChecked }
        itemView.setOnClickListener { onItemClicked(item) }

        tvName.text = item.name
        checkbox.isChecked = item.checked
    }
}

class ItemDiffCallback : DiffUtil.ItemCallback<Item>() {
    override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
        return oldItem == newItem
    }
}