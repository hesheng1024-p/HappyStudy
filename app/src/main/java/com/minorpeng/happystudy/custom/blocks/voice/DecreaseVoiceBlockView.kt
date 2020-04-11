package com.minorpeng.happystudy.custom.blocks.voice

import android.annotation.SuppressLint
import android.content.Context
import android.text.InputType
import android.util.AttributeSet
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.minorpeng.base.utils.DensityUtil
import com.minorpeng.happystudy.R
import com.minorpeng.happystudy.custom.base.BaseBgBlockView

/**
 *
 * @author MinorPeng
 * @date 2020/3/30 21:07
 */
@SuppressLint("ViewConstructor")
class DecreaseVoiceBlockView(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : BaseBgBlockView(context, attrs, defStyleAttr, defStyleRes) {

    init {
        setBgColorId(R.color.colorVoicePurple)
        initView()
    }

    private fun initView() {
        val tv = TextView(context)
        tv.setText(R.string.decrease_voice)
        tv.setTextColor(ContextCompat.getColor(context, android.R.color.white))
        addView(tv)

        val lp = generateDefaultLayoutParams() as MarginLayoutParams
        lp.leftMargin = DensityUtil.dp2px(context, 8f)
        val et = EditText(context)
        et.minEms = 2
        et.setText(R.string.ten)
        et.setBackgroundResource(R.drawable.bg_et_circle_whilte)
        et.inputType = InputType.TYPE_CLASS_NUMBER
        addView(et, lp)
    }

    override fun onRun(role: View) {

    }
}