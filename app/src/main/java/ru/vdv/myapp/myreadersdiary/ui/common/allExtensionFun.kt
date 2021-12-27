package ru.vdv.myapp.myreadersdiary.ui.common

import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import ru.vdv.myapp.myreadersdiary.R

/**
### "Установить соответствующий значению маркер"
 * @param count значение, в соответствии с которым маркируется элемент (в данном случае
 * количество баллов активности пользователя"
 * @param view ссылка на [ImageView] в которой следует установить маркер"
 **/
fun RecyclerView.ViewHolder.mark(count: Int, view: ImageView) {
        when (count) {
            0 -> view.setImageResource(R.drawable.round_square_indigo_50_24dp)
            in 1..2 -> view.setImageResource(R.drawable.round_square_indigo_100_24dp)
            in 3..4 -> view.setImageResource(R.drawable.round_square_indigo_200_24dp)
            in 5..6 -> view.setImageResource(R.drawable.round_square_indigo_300_24dp)
            in 7..8 -> view.setImageResource(R.drawable.round_square_indigo_400_24dp)
            in 9..10 -> view.setImageResource(R.drawable.round_square_indigo_500_24dp)
            in 11..12 -> view.setImageResource(R.drawable.round_square_indigo_600_24dp)
            in 13..14 -> view.setImageResource(R.drawable.round_square_indigo_700_24dp)
            in 15..16 -> view.setImageResource(R.drawable.round_square_indigo_800_24dp)
            in 17..10000 -> view.setImageResource(R.drawable.round_square_indigo_900_24dp)
        }
    }