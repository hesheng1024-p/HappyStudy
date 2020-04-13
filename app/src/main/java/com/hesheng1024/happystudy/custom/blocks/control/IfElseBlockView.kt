package com.hesheng1024.happystudy.custom.blocks.control

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
import com.hesheng1024.base.utils.DensityUtil
import com.hesheng1024.happystudy.R
import com.hesheng1024.happystudy.custom.base.BaseBlockViewGroup
import com.hesheng1024.happystudy.custom.base.IBaseBlock
import com.hesheng1024.happystudy.custom.blocks.calculate.LogicBgBlockView
import com.hesheng1024.happystudy.custom.blocks.motion.MoveBlockView
import kotlin.math.max

/**
 *
 * @author hesheng1024
 * @date 2020/4/1 11:03
 */
@SuppressLint("ViewConstructor")
class IfElseBlockView : BaseBlockViewGroup {

    private var mTopViewH = DensityUtil.dp2px(context, 32f).toFloat()
    private var mTopViewW = DensityUtil.dp2px(context, 150f).toFloat()
    private var mChildIfH = 0f
    private var mChildElseH = 0f

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : this(context, attrs, defStyleAttr, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int)
            : super(context, attrs, defStyleAttr, defStyleRes) {
        setBgColorId(R.color.colorControlYellow)
        this.setWillNotDraw(false)
        this.setPadding(
            (IBaseBlock.sDis2Top * 2).toInt(),
            IBaseBlock.sDis2Top.toInt(),
            (IBaseBlock.sDis2Top * 2).toInt(),
            (IBaseBlock.sDis2Top * 2)
                .toInt()
        )
        initView()
    }

    @SuppressLint("SetTextI18n")
    private fun initView() {
        val whiteColor = ContextCompat.getColor(context, android.R.color.white)

        val tvIf = TextView(context)
        tvIf.setText(R.string.if_str)
        tvIf.tag = ChildTag.TAG_TOP
        tvIf.setTextColor(whiteColor)
        addView(tvIf)

        val lp = generateDefaultLayoutParams() as MarginLayoutParams
        lp.leftMargin = DensityUtil.dp2px(context, 8f)
        lp.rightMargin = DensityUtil.dp2px(context, 8f)
        val logicBlockView = LogicBgBlockView(context)
        logicBlockView.setBgColorId(R.color.colorControlYellowDark)
        logicBlockView.tag = ChildTag.TAG_TOP
        addView(logicBlockView, lp)

        val tvThen = TextView(context)
        tvThen.setText(R.string.then)
        tvThen.tag = ChildTag.TAG_TOP
        tvThen.setTextColor(whiteColor)
        addView(tvThen)

        val tvElse = TextView(context)
        tvElse.setText(R.string.else_str)
        tvElse.tag = ChildTag.TAG_CENTER
        tvElse.setTextColor(whiteColor)
        addView(tvElse)

        // TODO test
        val mo = MoveBlockView(context)
        mo.tag = ChildTag.TAG_CHILD_IF
        addView(mo)

        val waitBlockView = WaitBlockView(context)
        waitBlockView.tag = ChildTag.TAG_CHILD_ELSE
        addView(waitBlockView)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val sizeW = MeasureSpec.getSize(widthMeasureSpec)
        val modeW = MeasureSpec.getMode(widthMeasureSpec)
        val sizeH = MeasureSpec.getSize(heightMeasureSpec)
        val modeH = MeasureSpec.getMode(heightMeasureSpec)

        var topViewW = 0
        var topViewMaxH = 0
        var centerViewW = 0
        var bottomViewW = 0

        var childMaxW = 0
        var childIfH = 0
        var childElseH = 0
        for (index in 0 until childCount) {
            val child = getChildAt(index)
            measureChild(child, widthMeasureSpec, heightMeasureSpec)
            val childLp = child.layoutParams as MarginLayoutParams
            when (child.tag) {
                ChildTag.TAG_TOP -> {
                    val childWidth = child.measuredWidth + childLp.leftMargin + childLp.rightMargin
                    val childHeight = child.measuredHeight + childLp.topMargin + childLp.bottomMargin
                    // top view width sum
                    topViewW += childWidth
                    // top view max height
                    topViewMaxH = max(topViewMaxH, childHeight)
                }
                ChildTag.TAG_CHILD_IF -> {
                    val childWidth = child.measuredWidth + childLp.leftMargin + childLp.rightMargin
                    val childHeight =
                        child.measuredHeight + childLp.topMargin + childLp.bottomMargin - IBaseBlock.sDis2Top.toInt()
                    // if view max width
                    childMaxW = max(childMaxW, childWidth)
                    // if view height sum
                    childIfH += childHeight
                }
                ChildTag.TAG_CENTER -> {
                    val childWidth = child.measuredWidth + childLp.leftMargin + childLp.rightMargin
                    val childHeight = child.measuredHeight + childLp.topMargin + childLp.bottomMargin
                    // center view width sum
                    centerViewW += childWidth
                    // center view max height
                    topViewMaxH = max(topViewMaxH, childHeight)
                }
                ChildTag.TAG_CHILD_ELSE -> {
                    val childWidth = child.measuredWidth + childLp.leftMargin + childLp.rightMargin
                    val childHeight =
                        child.measuredHeight + childLp.topMargin + childLp.bottomMargin - IBaseBlock.sDis2Top.toInt()
                    // else view max width
                    childMaxW = max(childMaxW, childWidth)
                    // else view height sum
                    childElseH += childHeight
                }
                ChildTag.TAG_BOTTOM -> {
                    val childWidth = child.measuredWidth + childLp.leftMargin + childLp.rightMargin
                    val childHeight = child.measuredHeight + childLp.topMargin + childLp.bottomMargin
                    // bottom view width sum
                    bottomViewW += childWidth
                    // bottom view max height
                    topViewMaxH = max(topViewMaxH, childHeight)
                }
                else -> {

                }
            }
        }
        mTopViewW = max(max(topViewW, max(centerViewW, bottomViewW)) + paddingLeft + paddingRight + 0f, mTopViewW)
        mTopViewH = max(topViewMaxH + paddingTop + paddingBottom - IBaseBlock.sDis2Top, mTopViewH)
        mChildIfH = childIfH.toFloat()
        mChildElseH = childElseH.toFloat()

        childIfH = if (childIfH == 0) mTopViewH.toInt() else childIfH
        childElseH = if (childElseH == 0) mTopViewH.toInt() else childElseH
        var width = max(mTopViewW, childMaxW + IBaseBlock.sDis2Left).toInt()
        var height = childIfH + childElseH + mTopViewH.toInt() * 3 + IBaseBlock.sDis2Top.toInt()

        width = if (modeW == MeasureSpec.EXACTLY) sizeW else width
        height = if (modeH == MeasureSpec.EXACTLY) sizeH else height
        setMeasuredDimension(width, height)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        var topL = paddingLeft
        var ifT = mTopViewH.toInt()
        var centerL = paddingLeft
        var elseT = (mChildIfH + mTopViewH * 2).toInt()
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
            when (child.tag) {
                ChildTag.TAG_TOP -> {
                    // layout from left to right
                    childL = topL + childLp.leftMargin
                    childT = (mTopViewH.toInt() - child.measuredHeight) / 2 + childLp.topMargin
                    childR = childL + child.measuredWidth
                    childB = childT + child.measuredHeight
                    topL += child.measuredWidth + childLp.leftMargin + childLp.rightMargin
                }
                ChildTag.TAG_CHILD_IF -> {
                    childL = IBaseBlock.sDis2Left.toInt() + childLp.leftMargin
                    childT = ifT + childLp.topMargin
                    childR = childL + child.measuredWidth
                    childB = childT + child.measuredHeight
                    ifT += child.measuredHeight + childLp.topMargin + childLp.bottomMargin
                }
                ChildTag.TAG_CENTER -> {
                    childL = centerL + childLp.leftMargin
                    childT =
                        (mTopViewH + mChildIfH + (mTopViewH - child.measuredHeight) / 2 + childLp.topMargin).toInt()
                    childR = childL + child.measuredWidth
                    childB = childT + child.measuredHeight
                    centerL += child.measuredWidth + childLp.leftMargin + childLp.rightMargin
                }
                ChildTag.TAG_CHILD_ELSE -> {
                    childL = IBaseBlock.sDis2Left.toInt() + childLp.leftMargin
                    childT = elseT + childLp.topMargin
                    childR = childL + child.measuredWidth
                    childB = childT + child.measuredHeight
                    elseT += child.measuredHeight + childLp.topMargin + childLp.bottomMargin
                }
                ChildTag.TAG_BOTTOM -> {
                    // layout from right to left
                    childL = mTopViewW.toInt() - child.measuredWidth - childLp.rightMargin - paddingRight
                    childT = measuredHeight - (mTopViewH.toInt() - child.measuredHeight) / 2 - child.measuredHeight -
                            IBaseBlock.sDis2Top.toInt()
                    childR = childL + child.measuredWidth
                    childB = childT + child.measuredHeight
                }
                else -> {

                }
            }
            child.layout(childL, childT, childR, childB)
        }
    }

    override fun drawBackground(canvas: Canvas, paint: Paint, path: Path, measuredW: Float, measuredH: Float) {
        path.reset()
        // top
        path.moveTo(0f, 0f)
        path.lineTo(IBaseBlock.sDis2Left, 0f)
        path.lineTo(IBaseBlock.sDis2Left + IBaseBlock.sDis2Top, IBaseBlock.sDis2Top)
        path.lineTo(IBaseBlock.sDis2Left + IBaseBlock.sDis2Top + IBaseBlock.sLineLen, IBaseBlock.sDis2Top)
        path.lineTo(IBaseBlock.sDis2Left + IBaseBlock.sDis2Top * 2 + IBaseBlock.sLineLen, 0f)
        path.lineTo(mTopViewW, 0f)

        path.lineTo(mTopViewW, mTopViewH)
        path.lineTo(IBaseBlock.sDis2Left * 2 + IBaseBlock.sDis2Top * 2 + IBaseBlock.sLineLen, mTopViewH)
        path.lineTo(IBaseBlock.sDis2Left * 2 + IBaseBlock.sDis2Top + IBaseBlock.sLineLen, mTopViewH + IBaseBlock.sDis2Top)
        path.lineTo(IBaseBlock.sDis2Left * 2 + IBaseBlock.sDis2Top, mTopViewH + IBaseBlock.sDis2Top)
        path.lineTo(IBaseBlock.sDis2Left * 2, mTopViewH)
        path.lineTo(IBaseBlock.sDis2Left, mTopViewH)
        // center
        val centerH = mChildIfH + mTopViewH
        path.lineTo(IBaseBlock.sDis2Left, centerH)
        path.lineTo(IBaseBlock.sDis2Left * 2, centerH)
        path.lineTo(IBaseBlock.sDis2Left * 2 + IBaseBlock.sDis2Top, centerH + IBaseBlock.sDis2Top)
        path.lineTo(IBaseBlock.sDis2Left * 2 + IBaseBlock.sDis2Top + IBaseBlock.sLineLen, centerH + IBaseBlock.sDis2Top)
        path.lineTo(IBaseBlock.sDis2Left * 2 + IBaseBlock.sDis2Top * 2 + IBaseBlock.sLineLen, centerH)
        path.lineTo(mTopViewW, centerH)

        path.lineTo(mTopViewW, centerH + mTopViewH)
        path.lineTo(IBaseBlock.sDis2Left * 2 + IBaseBlock.sDis2Top * 2 + IBaseBlock.sLineLen, centerH + mTopViewH)
        path.lineTo(IBaseBlock.sDis2Left * 2 + IBaseBlock.sDis2Top + IBaseBlock.sLineLen, centerH + mTopViewH + IBaseBlock.sDis2Top)
        path.lineTo(IBaseBlock.sDis2Left * 2 + IBaseBlock.sDis2Top, centerH + mTopViewH + IBaseBlock.sDis2Top)
        path.lineTo(IBaseBlock.sDis2Left * 2, centerH + mTopViewH)
        path.lineTo(IBaseBlock.sDis2Left, centerH + mTopViewH)
        // bottom
        path.lineTo(IBaseBlock.sDis2Left, measuredH - mTopViewH - IBaseBlock.sDis2Top)
        path.lineTo(IBaseBlock.sDis2Left * 2, measuredH - mTopViewH - IBaseBlock.sDis2Top)
        path.lineTo(IBaseBlock.sDis2Left * 2 + IBaseBlock.sDis2Top, (measuredH - mTopViewH))
        path.lineTo(IBaseBlock.sDis2Left * 2 + IBaseBlock.sDis2Top + IBaseBlock.sLineLen, (measuredH - mTopViewH))
        path.lineTo(IBaseBlock.sDis2Left * 2 + IBaseBlock.sDis2Top * 2 + IBaseBlock.sLineLen, measuredH - mTopViewH - IBaseBlock.sDis2Top)
        path.lineTo(mTopViewW, measuredH - mTopViewH - IBaseBlock.sDis2Top)

        path.lineTo(mTopViewW, measuredH - IBaseBlock.sDis2Top)
        path.lineTo(IBaseBlock.sDis2Left + IBaseBlock.sDis2Top * 2 + IBaseBlock.sLineLen, measuredH - IBaseBlock.sDis2Top)
        path.lineTo(IBaseBlock.sDis2Left + IBaseBlock.sDis2Top + IBaseBlock.sLineLen, measuredH)
        path.lineTo(IBaseBlock.sDis2Left + IBaseBlock.sDis2Top, measuredH)
        path.lineTo(IBaseBlock.sDis2Left, measuredH - IBaseBlock.sDis2Top)
        path.lineTo(0f, measuredH - IBaseBlock.sDis2Top)
        path.close()

        paint.style = Paint.Style.FILL
        paint.color = getBgColor()
        paint.pathEffect = CornerPathEffect(IBaseBlock.sRadius)
        canvas.drawPath(path, paint)
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = IBaseBlock.sStrokeW
        paint.color = getBgBorderColor()
        canvas.drawPath(path, paint)
    }

    override fun onRun(role: View) {

    }

    override fun clone(): IBaseBlock {
        val newObj = IfElseBlockView(context)
        newObj.layoutParams = this.layoutParams
        newObj.minimumWidth = measuredWidth
        newObj.minimumHeight = measuredHeight
        return newObj
    }

    private enum class ChildTag(tag: String) {
        TAG_TOP("tag_top"),
        TAG_CENTER("tag_center"),
        TAG_BOTTOM("tag_bottom"),
        TAG_CHILD_IF("tag_child_if"),
        TAG_CHILD_ELSE("tag_child_if_else")
    }
}