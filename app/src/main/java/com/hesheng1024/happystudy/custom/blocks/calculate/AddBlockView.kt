package com.hesheng1024.happystudy.custom.blocks.calculate

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import com.hesheng1024.happystudy.R
import com.hesheng1024.happystudy.custom.blocks.base.IBaseBlock
import com.hesheng1024.happystudy.custom.role.IRoleView

/**
 *
 * @author hesheng1024
 * @date 2020/4/3 16:46
 */
@SuppressLint("ViewConstructor")
class AddBlockView: BaseCalculateBlockView {
    
    private val mLeftCalculateBg: CalculateBgBlock
    private val mRightCalculateBg: CalculateBgBlock

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : this(context, attrs, defStyleAttr, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int)
            : super(context, attrs, defStyleAttr, defStyleRes) {
        View.inflate(context, R.layout.layout_add_block, this)
        mLeftCalculateBg = findViewById(R.id.bg_add_block_left)
        mRightCalculateBg = findViewById(R.id.bg_add_block_right)
        initView()
    }

    private fun initView() {
    }

    override suspend fun onRun(role: IRoleView) {

    }

    override fun clone(): IBaseBlock {
        val newObj = AddBlockView(context)
        newObj.layoutParams = this.layoutParams
        newObj.minimumWidth = measuredWidth
        newObj.minimumHeight = measuredHeight
        when (val child = mLeftCalculateBg.getChildAt(0)) {
            is AppCompatEditText -> {
                if (newObj.mLeftCalculateBg.getChildAt(0) is AppCompatEditText) {
                    (newObj.mLeftCalculateBg.getChildAt(0) as AppCompatEditText).setText(child.text.toString())
                } else {
                    val newEt = AppCompatEditText(context)
                    newEt.setText(child.text.toString())
                    newObj.mLeftCalculateBg.addView(newEt, 0)
                }
            }
            is BaseCalculateBlockView -> {
                newObj.mLeftCalculateBg.addView(child.clone() as BaseCalculateBlockView)
            }
        }
        when (val child = mRightCalculateBg.getChildAt(0)) {
            is AppCompatEditText -> {
                if (newObj.mRightCalculateBg.getChildAt(0) is AppCompatEditText) {
                    (newObj.mRightCalculateBg.getChildAt(0) as AppCompatEditText).setText(child.text.toString())
                } else {
                    val newEt = AppCompatEditText(context)
                    newEt.setText(child.text.toString())
                    newObj.mRightCalculateBg.addView(newEt, 0)
                }
            }
            is BaseCalculateBlockView -> {
                newObj.mRightCalculateBg.addView(child.clone() as BaseCalculateBlockView)
            }
        }
        return newObj
    }

    override fun calculateResult(): Float = mLeftCalculateBg.calculateResult() + mRightCalculateBg.calculateResult()
}