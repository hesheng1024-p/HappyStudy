package com.hesheng1024.happystudy.custom.blocks.draw

import android.content.Context
import android.text.InputType
import android.util.AttributeSet
import android.view.View
import com.hesheng1024.happystudy.R
import com.hesheng1024.happystudy.custom.blocks.BlockEditText
import com.hesheng1024.happystudy.custom.blocks.base.BaseRelativeBlockView
import com.hesheng1024.happystudy.custom.blocks.base.IBaseBlock
import com.hesheng1024.happystudy.custom.blocks.calculate.CalculateBgBlock
import com.hesheng1024.happystudy.custom.role.IRoleView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 *
 * @author hesheng1024
 * @date 2020/5/13 10:39
 */
class DrawPointBlockView : BaseRelativeBlockView {

    private val mCalBgCx: CalculateBgBlock
    private val mCalBgCy: CalculateBgBlock
    private val mCalBgR: CalculateBgBlock
    private val mEtName: BlockEditText
    private val mSpinnerColor: DrawColorSpinner

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : this(context, attrs, defStyleAttr, 0) {
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int)
            : super(context, attrs, defStyleAttr, defStyleRes) {
        setBgColorId(R.color.colorDrawRed500)
        View.inflate(context, R.layout.layout_draw_point_block, this)
        mCalBgCx = findViewById(R.id.cal_bg_draw_point_block_cx)
        mCalBgCy = findViewById(R.id.cal_bg_draw_point_block_cy)
        mCalBgR = findViewById(R.id.cal_bg_draw_point_block_r)
        mEtName = findViewById(R.id.et_draw_point_block_name)
        mSpinnerColor = findViewById(R.id.spinner_draw_point_block_color)
        initView()
    }

    private fun initView() {
        mEtName.inputType = InputType.TYPE_CLASS_TEXT
    }

    override fun clone(): IBaseBlock {
        val newObj = DrawPointBlockView(context)
        newObj.layoutParams = this.layoutParams
        newObj.minimumWidth = measuredWidth
        newObj.minimumHeight = measuredHeight
        newObj.mCalBgCx.clone(mCalBgCx)
        newObj.mCalBgCy.clone(mCalBgCy)
        newObj.mCalBgR.clone(mCalBgR)
        newObj.mEtName.setText(mEtName.text.toString())
        newObj.mSpinnerColor.clone(mSpinnerColor)
        return newObj
    }

    override suspend fun onRun(role: IRoleView) {
        GlobalScope.launch(Dispatchers.Main) {
            val cx = mCalBgCx.calculateResult()
            val cy = mCalBgCy.calculateResult()
            val r = mCalBgR.calculateResult()
            val name = mEtName.text.toString()
            val color = mSpinnerColor.getSelectedColor()
            role.drawPoint(cx, cy, r, color, name)
        }
    }
}