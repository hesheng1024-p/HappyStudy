package com.hesheng1024.happystudy.custom.base

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.Gravity
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import com.hesheng1024.base.utils.LogUtil

/**
 *
 * @author hesheng1024
 * @date 2020/3/29 19:40
 */
abstract class BaseTextBlockView : AppCompatTextView, IBaseBlock {

    private val mPaint = Paint()
    private val mPath = Path()
    /**
     * 积木的背景色
     */
    @Volatile
    private var mBgColor = -1
    @Volatile
    private var mStatus = IBaseBlock.Status.STATUS_CLONE
    @Volatile
    private var mInRectF = false
    private var mBlackOwn: IBaseBlock? = null

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        gravity = Gravity.CENTER
        this.setPadding(
            (IBaseBlock.sDis2Top * 2).toInt(),
            (IBaseBlock.sDis2Top * 2).toInt(),
            (IBaseBlock.sDis2Top * 2).toInt(),
            (IBaseBlock.sDis2Top * 2).toInt()
        )
        this.setTextColor(ContextCompat.getColor(context, android.R.color.white))
        this.setOnDragListener(this)
        this.setOnTouchListener(this)
    }

    override fun onDraw(canvas: Canvas?) {
        canvas?.let {
            drawBackground(canvas, mPaint, mPath, measuredWidth.toFloat(), measuredHeight.toFloat())
        }
        super.onDraw(canvas)
    }

    override fun setBgColorId(colorId: Int) {
        this.mBgColor = ContextCompat.getColor(context, colorId)
    }

    override fun setBgColor(color: Int) {
        this.mBgColor = color
    }

    override fun getBgColor(): Int {
        return mBgColor
    }

    override fun setStatus(status: IBaseBlock.Status) {
        this.mStatus = status
    }

    override fun getStatus(): IBaseBlock.Status {
        return mStatus
    }

    @Synchronized
    override fun getBlackOwn(): IBaseBlock {
        if (mBlackOwn == null) {
            mBlackOwn = clone()
            mBlackOwn?.setBgColor(getBgBorderColor())
            mBlackOwn?.setStatus(IBaseBlock.Status.STATUS_NONE)
            if (mBlackOwn is BaseTextBlockView) {
                (mBlackOwn as BaseTextBlockView).text = ""
            }
        }
        return mBlackOwn!!
    }

    override fun inTopRectF(x: Float, y: Float): Boolean {
        val isIn = if (mInRectF) {
            x < measuredWidth / 2 && x > 0 && y < measuredHeight / 2 && y > 0
        } else {
            x < left + measuredWidth / 2 && x > left && y < top && y > top - measuredHeight / 2 * 3
        }
        LogUtil.d(msg = "isIn:$isIn")
        return isIn
    }

    override fun inBottomRectF(x: Float, y: Float): Boolean {
        val isIn = if (mInRectF) {
            x < measuredWidth / 2 && x > 0 && y < measuredHeight && y > measuredHeight / 2
        } else {
            x < left + measuredWidth / 2 && x > left && y < top + measuredHeight / 2 * 3 && y > top
        }
        LogUtil.d(msg = "isIn:$isIn")
        return isIn
    }

    override fun isInRectF(inRectF: Boolean) {
        this.mInRectF = inRectF
    }
}