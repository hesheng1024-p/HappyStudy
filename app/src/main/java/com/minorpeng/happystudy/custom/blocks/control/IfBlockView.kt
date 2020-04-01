package com.minorpeng.happystudy.custom.blocks.control

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.CornerPathEffect
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.minorpeng.base.utils.DensityUtil
import com.minorpeng.happystudy.R
import com.minorpeng.happystudy.custom.base.BaseBlockViewGroup
import com.minorpeng.happystudy.custom.blocks.motion.MoveBlockView
import kotlin.math.max

/**
 *
 * @author MinorPeng
 * @date 2020/4/1 11:03
 */
class IfBlockView : BaseBlockViewGroup {

    private val mDis2Left = DensityUtil.dp2px(context, 10f).toFloat()
    private val mDis2Top = DensityUtil.dp2px(context, 4f).toFloat()
    private val mLineLen = DensityUtil.dp2px(context, 12f).toFloat()
    private var mTopViewH = 0f
    private var mTopViewW = 0f
    private val mRadius = 6f
    private val mPaint = Paint()

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int)
            : this(context, attrs, defStyleAttr, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int)
            : super(context, attrs, defStyleAttr, defStyleRes) {
        this.setWillNotDraw(false)
        this.setPadding((mDis2Top * 2).toInt(), mDis2Top.toInt(), (mDis2Top * 2).toInt(), (mDis2Top * 2).toInt())
        initView()
    }

    @SuppressLint("SetTextI18n")
    private fun initView() {
        val whiteColor = ContextCompat.getColor(context, android.R.color.white)
        val lp = MarginLayoutParams(MarginLayoutParams.WRAP_CONTENT, MarginLayoutParams.WRAP_CONTENT)
        val tvCirculation = TextView(context)
        tvCirculation.setText(R.string.circulation)
        tvCirculation.tag = ChildTag.TAG_TOP
        tvCirculation.setTextColor(whiteColor)
        tvCirculation.layoutParams = lp
        addView(tvCirculation)

        // TODO test
        val mo = MoveBlockView(context)
        mo.layoutParams = lp
        mo.tag = ChildTag.TAG_CHILD
        addView(mo)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val sizeW = MeasureSpec.getSize(widthMeasureSpec)
        val modeW = MeasureSpec.getMode(widthMeasureSpec)
        val sizeH = MeasureSpec.getSize(heightMeasureSpec)
        val modeH = MeasureSpec.getMode(heightMeasureSpec)

        var width = 0
        var height = 0
        var topChildMaxH = 0
        var childMaxW = 0
        for (index in 0 until childCount) {
            val child = getChildAt(index)
            measureChild(child, widthMeasureSpec, heightMeasureSpec)
            val childLp = child.layoutParams as MarginLayoutParams
            when(child.tag) {
                ChildTag.TAG_TOP -> {
                    val childWidth = child.measuredWidth + childLp.leftMargin + childLp.rightMargin
                    val childHeight = child.measuredHeight + childLp.topMargin + childLp.bottomMargin
                    // top view width sum
                    width += childWidth
                    // top view max height
                    topChildMaxH = max(topChildMaxH, childHeight)
                }
                ChildTag.TAG_CHILD -> {
                    val childWidth = child.measuredWidth + childLp.leftMargin + childLp.rightMargin
                    val childHeight = child.measuredHeight + childLp.topMargin + childLp.bottomMargin - mDis2Top.toInt()
                    // center view max width
                    childMaxW = max(childMaxW, childWidth)
                    // center view height sum
                    height += childHeight
                }
                else -> {

                }
            }
        }
        mTopViewW = (width + paddingLeft + paddingRight).toFloat()
        mTopViewH = topChildMaxH + paddingTop + paddingBottom - mDis2Top
        if (height == 0) {
            height = mTopViewH.toInt()
        }
        width = max(mTopViewW, childMaxW + mDis2Left).toInt()
        height += mTopViewH.toInt() * 2 + mDis2Top.toInt()

        width = if (modeW == MeasureSpec.EXACTLY) sizeW else width
        height = if (modeH == MeasureSpec.EXACTLY) sizeH else height
        setMeasuredDimension(width, height)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        var left = paddingLeft
        var top = mTopViewH.toInt()
        for (index in 0 until childCount) {
            val child = getChildAt(index)
            if (child.visibility == View.GONE) {
                continue
            }
            val childLp = child.layoutParams as MarginLayoutParams
            var childL = 0
            var childT = 0
            var childR = 0
            var childB = 0
            when(child.tag) {
                ChildTag.TAG_TOP -> {
                    childL = left + childLp.leftMargin
                    childT = (mTopViewH.toInt() - child.measuredHeight) / 2 + childLp.topMargin
                    childR = childL + child.measuredWidth
                    childB = childT + child.measuredHeight
                    left += child.measuredWidth + childLp.leftMargin + childLp.rightMargin
                }
                ChildTag.TAG_CHILD -> {
                    childL = mDis2Left.toInt() + childLp.leftMargin
                    childT = top + childLp.topMargin
                    childR = childL + child.measuredWidth
                    childB = childT + child.measuredHeight
                    top += child.measuredHeight + childLp.topMargin + childLp.bottomMargin
                }
                else -> {

                }
            }
            child.layout(childL, childT, childR, childB)
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.let {
            drawBackground(canvas)
        }
    }

    private fun drawBackground(canvas: Canvas) {
        val path = Path()
        // top
        path.moveTo(0f, 0f)
        path.lineTo(mDis2Left, 0f)
        path.lineTo(mDis2Left + mDis2Top, mDis2Top)
        path.lineTo(mDis2Left + mDis2Top + mLineLen, mDis2Top)
        path.lineTo(mDis2Left + mDis2Top * 2 + mLineLen, 0f)
        path.lineTo(mTopViewW, 0f)

        path.lineTo(mTopViewW, mTopViewH)
        path.lineTo(mDis2Left * 2 + mDis2Top * 2 + mLineLen, mTopViewH)
        path.lineTo(mDis2Left * 2 + mDis2Top + mLineLen, mTopViewH + mDis2Top)
        path.lineTo(mDis2Left * 2 + mDis2Top, mTopViewH + mDis2Top)
        path.lineTo(mDis2Left * 2, mTopViewH)
        path.lineTo(mDis2Left, mTopViewH)
        // bottom
        path.lineTo(mDis2Left, measuredHeight - mTopViewH - mDis2Top)
        path.lineTo(mDis2Left * 2, measuredHeight - mTopViewH - mDis2Top)
        path.lineTo(mDis2Left * 2 + mDis2Top, (measuredHeight - mTopViewH))
        path.lineTo(mDis2Left * 2 + mDis2Top + mLineLen, (measuredHeight - mTopViewH))
        path.lineTo(mDis2Left * 2 + mDis2Top * 2 + mLineLen, measuredHeight - mTopViewH - mDis2Top)
        path.lineTo(mTopViewW, measuredHeight - mTopViewH - mDis2Top)

        path.lineTo(mTopViewW, measuredHeight - mDis2Top)
        path.lineTo(mDis2Left + mDis2Top * 2 + mLineLen, measuredHeight - mDis2Top)
        path.lineTo(mDis2Left + mDis2Top + mLineLen, measuredHeight.toFloat())
        path.lineTo(mDis2Left + mDis2Top, measuredHeight.toFloat())
        path.lineTo(mDis2Left, measuredHeight - mDis2Top)
        path.lineTo(0f, measuredHeight - mDis2Top)
        path.close()

        mPaint.style = Paint.Style.FILL
        mPaint.color = ContextCompat.getColor(context, R.color.colorControlYellow)
        mPaint.pathEffect = CornerPathEffect(mRadius)
        canvas.drawPath(path, mPaint)
    }

    override fun generateLayoutParams(attrs: AttributeSet?): LayoutParams {
        return MarginLayoutParams(context, attrs)
    }

    override fun onRun(role: View) {

    }
}