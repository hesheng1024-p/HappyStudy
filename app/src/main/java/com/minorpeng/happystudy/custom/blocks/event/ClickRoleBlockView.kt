package com.minorpeng.happystudy.custom.blocks.event

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.minorpeng.base.utils.DensityUtil
import com.minorpeng.happystudy.R
import com.minorpeng.happystudy.custom.base.BaseTextBlockView
import com.minorpeng.happystudy.custom.base.IBaseBlockBg

/**
 *
 * @author MinorPeng
 * @date 2020/3/30 21:44
 */
class ClickRoleBlockView(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    BaseTextBlockView(context, attrs, defStyleAttr) {

    private val mDisTop = DensityUtil.dp2px(context, 16f).toFloat()

    init {
        setBgColorId(R.color.colorEventYellow)
        this.setPadding(
            (IBaseBlockBg.sDis2Top * 2).toInt(),
            (IBaseBlockBg.sDis2Top * 2 + mDisTop).toInt(),
            (IBaseBlockBg.sDis2Top * 2).toInt(),
            (IBaseBlockBg.sDis2Top * 3).toInt()
        )
        setText(R.string.click_role)
    }

    override fun drawBackground(canvas: Canvas, paint: Paint, path: Path, measuredW: Float, measuredH: Float) {
        val rectF = RectF(-mDisTop, 0f, measuredWidth - IBaseBlockBg.sLineLen, measuredWidth - IBaseBlockBg.sLineLen + mDisTop * 2)
        path.reset()
        path.moveTo(0f, mDisTop)
        path.arcTo(rectF, -138f, 90f)
        path.lineTo(measuredW, mDisTop)
        path.lineTo(measuredW, measuredHeight - IBaseBlockBg.sDis2Top)
        path.lineTo(IBaseBlockBg.sDis2Left + IBaseBlockBg.sDis2Top * 2 + IBaseBlockBg.sLineLen, measuredHeight - IBaseBlockBg.sDis2Top)
        path.lineTo(IBaseBlockBg.sDis2Left + IBaseBlockBg.sDis2Top + IBaseBlockBg.sLineLen, measuredH)
        path.lineTo(IBaseBlockBg.sDis2Left + IBaseBlockBg.sDis2Top, measuredH)
        path.lineTo(IBaseBlockBg.sDis2Left, measuredH - IBaseBlockBg.sDis2Top)
        path.lineTo(0f, measuredH - IBaseBlockBg.sDis2Top)
        path.lineTo(0f, IBaseBlockBg.sDis2Top)
        paint.style = Paint.Style.FILL
        paint.color = getBgColor()
        paint.pathEffect = CornerPathEffect(IBaseBlockBg.sRadius)
        canvas.drawPath(path, paint)
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = IBaseBlockBg.sStrokeW
        paint.color = getBgBorderColor()
        canvas.drawPath(path, paint)
    }

    override fun onRun(role: View) {

    }
}