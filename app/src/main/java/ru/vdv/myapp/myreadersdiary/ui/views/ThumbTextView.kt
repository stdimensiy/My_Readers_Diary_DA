package ru.vdv.myapp.myreadersdiary.ui.views

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import android.widget.SeekBar
import androidx.appcompat.widget.AppCompatTextView

class ThumbTextView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) : AppCompatTextView(context, attrs) {
    private val tvLayoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
    private var tvWidth = 0

    fun attachToSeekBar(seekBar: SeekBar) {
        text.toString().takeIf { it.isNotEmpty() }?.let { content ->
            val contentWidth = this.paint.measureText(content)
            val realWidth = tvWidth - seekBar.paddingLeft - seekBar.paddingRight
            val maxLimit = (tvWidth - contentWidth - seekBar.paddingRight).toInt()
            val minLimit = seekBar.paddingLeft
            val percent = (1.0 * seekBar.progress / seekBar.max).toFloat()
            var left = minLimit + (realWidth * percent - contentWidth / 2.0).toInt()
            left =
                if (left <= minLimit) minLimit
                else left.coerceAtMost(maxLimit)
            tvLayoutParams.setMargins(left, 0, 0, 0)
            layoutParams = tvLayoutParams
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        if (tvWidth == 0) tvWidth = MeasureSpec.getSize(widthMeasureSpec)
    }
}
