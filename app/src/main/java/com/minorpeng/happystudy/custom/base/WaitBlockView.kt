package com.minorpeng.happystudy.custom.base

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.minorpeng.base.utils.DensityUtil
import com.minorpeng.happystudy.R

/**
 *
 * @author MinorPeng
 * @date 2020/4/1 20:28
 */
class WaitBlockView : BaseBgBlockView {

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int)
            : this(context, attrs, defStyleAttr, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int)
            : super(context, attrs, defStyleAttr, defStyleRes) {
        initView()
    }

    private fun initView() {
        val whiteColor = ContextCompat.getColor(context, android.R.color.white)
        val lp = MarginLayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
        val tvWait = TextView(context)
        tvWait.setText(R.string.wait)
        tvWait.setTextColor(whiteColor)
        tvWait.layoutParams = lp
        addView(tvWait)

        lp.leftMargin = DensityUtil.dp2px(context, 8f)
        val etSeconds = EditText(context)
        etSeconds.setText(R.string.ten)
        etSeconds.setBackgroundResource(R.drawable.bg_et_circle_whilte)
        etSeconds.gravity = Gravity.CENTER
        etSeconds.layoutParams = lp
        addView(etSeconds)

        val tvSeconds = TextView(context)
        tvSeconds.setText(R.string.seconds)
        tvSeconds.setTextColor(whiteColor)
        tvSeconds.layoutParams = lp
        addView(tvSeconds)
    }

    override fun getBgColorId(): Int {
        return R.color.colorControlYellow
    }

    override fun onRun(role: View) {

    }
}