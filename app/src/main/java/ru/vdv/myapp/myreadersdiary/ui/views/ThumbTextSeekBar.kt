package ru.vdv.myapp.myreadersdiary.ui.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import ru.vdv.myapp.myreadersdiary.R

class ThumbTextSeekBar @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) : LinearLayout(context, attrs) {
    private var tvThumb: ThumbTextView? = null
    private var seekBar: SeekBar? = null
    private var onSeekBarChangeListener: OnSeekBarChangeListener? = null

    init {
        LayoutInflater.from(context).inflate(R.layout.view_thumb_text_seekbar, this)
        orientation = VERTICAL
        tvThumb = findViewById(R.id.tvThumb)
        seekBar = findViewById(R.id.sbProgress)
        seekBar?.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onStopTrackingTouch(seekBar: SeekBar) {
                hideText()
                onSeekBarChangeListener?.onStopTrackingTouch(seekBar)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
                showText()
                onSeekBarChangeListener?.onStartTrackingTouch(seekBar)
            }

            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                setThumbText(seekBar.progress.toString())
                onSeekBarChangeListener?.onProgressChanged(seekBar, progress, fromUser)
                tvThumb?.attachToSeekBar(seekBar)
            }
        })
    }

    fun setMaxValue(maxValue: Int) {
        seekBar?.max = maxValue
    }

    fun setOnSeekBarChangeListener(l: OnSeekBarChangeListener) {
        onSeekBarChangeListener = l
    }

    fun setProgress(progress: Int) {
        if (progress == seekBar?.progress && progress == 0) {
            seekBar?.progress = 1
            seekBar?.progress = 0
        } else {
            seekBar?.progress = progress
        }
    }

    private fun setThumbText(text: String?) {
        tvThumb?.text = text
    }

    private fun showText() {
        tvThumb?.visibility = VISIBLE
    }

    private fun hideText() {
        tvThumb?.visibility = INVISIBLE
    }
}
