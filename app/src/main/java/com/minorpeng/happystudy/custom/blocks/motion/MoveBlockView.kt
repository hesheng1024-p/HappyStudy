package com.minorpeng.happystudy.custom.blocks.motion

import android.annotation.SuppressLint
import android.content.Context
import android.text.InputType
import android.util.AttributeSet
import android.view.Gravity
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
 * @date 2020/3/24 18:12
 */
@SuppressLint("ViewConstructor")
class MoveBlockView(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0, defStyleRes: Int = 0) :
    BaseBgBlockView(context, attrs, defStyleAttr, defStyleRes) {

    init {
        setBgColorId(R.color.colorMotionBlue)
        initView()
    }

    private fun initView() {
        val whiteColor = ContextCompat.getColor(context, android.R.color.white)
        val tv = TextView(context)
        tv.setText(R.string.move)
        tv.setTextColor(whiteColor)
        addView(tv)

        val lp = generateDefaultLayoutParams() as MarginLayoutParams
        lp.leftMargin = DensityUtil.dp2px(context, 8f)
        lp.rightMargin = DensityUtil.dp2px(context, 8f)
        val et = EditText(context)
        et.minEms = 2
        et.setText(R.string.ten)
        et.setBackgroundResource(R.drawable.bg_et_circle_whilte)
        et.inputType = InputType.TYPE_CLASS_NUMBER
        et.gravity = Gravity.CENTER
        addView(et, lp)

        val tvStep = TextView(context)
        tvStep.setText(R.string.step)
        tvStep.setTextColor(whiteColor)
        addView(tvStep)
    }

    override fun onRun(role: View) {
    }
}