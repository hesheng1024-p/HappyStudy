package com.hesheng1024.happystudy.custom.blocks.calculate

import android.annotation.SuppressLint
import android.content.Context
import android.text.InputType
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import com.hesheng1024.base.utils.DensityUtil
import com.hesheng1024.happystudy.R
import com.hesheng1024.happystudy.custom.base.IBaseBlock

/**
 *
 * @author hesheng1024
 * @date 2020/4/3 16:50
 */
@SuppressLint("ViewConstructor")
class MultiplyBlockView : BaseCalculateBlockView {

    private val mEtLeft: AppCompatEditText
    private val mEtRight: AppCompatEditText

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : this(context, attrs, defStyleAttr, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int)
            : super(context, attrs, defStyleAttr, defStyleRes) {
        mEtLeft = AppCompatEditText(context)
        mEtRight = AppCompatEditText(context)
        initView()
    }

    private fun initView() {
        mEtLeft.setBackgroundResource(R.drawable.bg_et_circle_whilte)
        mEtLeft.gravity = Gravity.CENTER
        mEtLeft.inputType = InputType.TYPE_CLASS_NUMBER
        mEtLeft.minEms = 2
        mEtLeft.setOnDragListener { v, event ->
            return@setOnDragListener true
        }
        addView(mEtLeft)

        val lpTvMultiply = generateDefaultLayoutParams() as MarginLayoutParams
        lpTvMultiply.leftMargin = DensityUtil.dp2px(context, 8f)
        lpTvMultiply.rightMargin = DensityUtil.dp2px(context, 8f)
        val tvMultiply = TextView(context)
        tvMultiply.setText(R.string.multiply)
        tvMultiply.setTextColor(ContextCompat.getColor(context, android.R.color.white))
        addView(tvMultiply, lpTvMultiply)

        mEtRight.setBackgroundResource(R.drawable.bg_et_circle_whilte)
        mEtRight.gravity = Gravity.CENTER
        mEtRight.inputType = InputType.TYPE_CLASS_NUMBER
        mEtRight.setText(R.string.fifty)
        mEtRight.minEms = 2
        mEtRight.setOnDragListener { v, event ->
            return@setOnDragListener true
        }
        addView(mEtRight)
    }

    override fun onRun(role: View) {

    }

    override fun clone(): IBaseBlock {
        val newObj = MultiplyBlockView(context)
        newObj.layoutParams = this.layoutParams
        newObj.mEtLeft.setText(this.mEtLeft.text.toString())
        newObj.mEtRight.setText(this.mEtRight.text.toString())
        return newObj
    }
}