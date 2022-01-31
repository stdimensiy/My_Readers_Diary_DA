package ru.vdv.myapp.myreadersdiary.ui.common.viewholders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.vdv.myapp.myreadersdiary.databinding.ItemListUserCardBinding

class UserCardViewHolder(
    li: LayoutInflater,
    parent: ViewGroup,
    binding: ItemListUserCardBinding = ItemListUserCardBinding.inflate(li, parent, false)
) : RecyclerView.ViewHolder(binding.root) {
    var userAvatar = binding.ivUserAvatar
    var userBackground = binding.ivUserCustomBg
    var materialCardView = binding.mcvUserCard
}