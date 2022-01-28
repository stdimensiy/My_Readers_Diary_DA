package ru.vdv.myapp.myreadersdiary.ui.common.viewholders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.vdv.myapp.myreadersdiary.databinding.ItemListUnknownTypeBinding

class UnknownTypeViewHolder(
    li: LayoutInflater,
    parent: ViewGroup,
    val binding: ItemListUnknownTypeBinding = ItemListUnknownTypeBinding.inflate(li, parent, false)
) : RecyclerView.ViewHolder(binding.root) {
    var title = binding.tvUnknownTypeTitle
    var body = binding.tvUnknownTypeBody
    var name = binding.tvUnknownTypeName
    var sign = binding.ivUnknownTypeSign
}