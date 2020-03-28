package com.minorpeng.happystudy.custom.blocks.sport

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import com.minorpeng.happystudy.R
import com.minorpeng.happystudy.custom.base.BaseBlockView

/**
 *
 * @author MinorPeng
 * @date 2020/3/28 19:09
 */
class AddYBlockView : BaseBlockView {

    private var mDis2Left = 8f * 4
    private var mDis2Top = 2f * 4
    private var mLineLen = 4f * 4
    private val mPaint = Paint()
    private val mRootView: LinearLayout

    constructor(context: Context): this(context, null)

    constructor(context: Context, attrs: AttributeSet?): this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int): super(context, attrs, defStyleAttr, 0) {
        mRootView = LayoutInflater.from(context).inflate(R.layout.layout_add_y_block, this, true) as LinearLayout
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.let {
            drawBackground(canvas)
        }
    }

    private fun drawBackground(canvas: Canvas) {
        val path = Path()
        path.moveTo(0f, 0f)
        path.lineTo(mDis2Left, 0f)
        path.lineTo(mDis2Left + mDis2Top, mDis2Top)
        path.lineTo(mDis2Left + mDis2Top + mLineLen, mDis2Top)
        path.lineTo(mDis2Left + mDis2Top * 2 + mLineLen, 0f)
        path.lineTo(measuredWidth.toFloat(), 0f)
        path.lineTo(measuredWidth.toFloat(), measuredHeight.toFloat())
        path.lineTo(mDis2Left + mDis2Top * 2 + mLineLen, measuredHeight.toFloat())
        path.lineTo(mDis2Left + mDis2Top + mLineLen, measuredHeight + mDis2Top)
        path.lineTo(mDis2Left + mDis2Top, measuredHeight + mDis2Top)
        path.lineTo(mDis2Left, measuredHeight.toFloat())
        path.lineTo(0f, measuredHeight.toFloat())
        path.lineTo(0f, 0f)
        mPaint.style = Paint.Style.FILL
        mPaint.color = ContextCompat.getColor(context, android.R.color.holo_blue_dark)
        canvas.drawPath(path, mPaint)
    }

    override fun onRun(role: View) {
    }
}