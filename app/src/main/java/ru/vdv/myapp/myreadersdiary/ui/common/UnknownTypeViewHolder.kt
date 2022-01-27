package ru.vdv.myapp.myreadersdiary.ui.common

import androidx.recyclerview.widget.RecyclerView
import ru.vdv.myapp.myreadersdiary.databinding.UnknownTypeListItemBinding

class UnknownTypeViewHolder(binding: UnknownTypeListItemBinding): RecyclerView.ViewHolder(binding.root) {
    var title = binding.tvUnknownTypeTitle
    var body = binding.tvUnknownTypeBody
    var name = binding.tvUnknownTypeName
    var sign = binding.ivUnknownTypeSign
}