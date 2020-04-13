package com.hesheng1024.happystudy.custom.blocks.appearance

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.hesheng1024.base.utils.DensityUtil
import com.hesheng1024.happystudy.R
import com.hesheng1024.happystudy.custom.base.BaseBgBlockView
import com.hesheng1024.happystudy.custom.base.IBaseBlock

/**
 *
 * @author hesheng1024
 * @date 2020/3/29 19:34
 */
@SuppressLint("ViewConstructor")
class SayBlockView : BaseBgBlockView {

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        setBgColorId(R.color.colorAppearancePurple)
        initView()
    }

    private fun initView() {
        val tv = TextView(context)
        tv.setText(R.string.say)
        tv.setTextColor(ContextCompat.getColor(context, android.R.color.white))
        addView(tv)

        val lp = generateDefaultLayoutParams() as MarginLayoutParams
        lp.leftMargin = DensityUtil.dp2px(context, 8f)
        val et = EditText(context)
        et.minEms = 2
        et.setText(R.string.hello)
        et.setBackgroundResource(R.drawable.bg_et_circle_whilte)
        addView(et, lp)
    }

    override fun onRun(role: View) {

    }

    override fun clone(): IBaseBlock {
        val newObj = SayBlockView(context)
        newObj.layoutParams = this.layoutParams
        newObj.minimumWidth = measuredWidth
        newObj.minimumHeight = measuredHeight
        return newObj
    }
}