package com.hesheng1024.happystudy.custom.blocks.control

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.DragEvent
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.hesheng1024.base.utils.DensityUtil
import com.hesheng1024.base.utils.LogUtil
import com.hesheng1024.happystudy.R
import com.hesheng1024.happystudy.custom.base.IBaseBlock
import com.hesheng1024.happystudy.custom.base.IRoleView
import com.hesheng1024.happystudy.custom.blocks.calculate.BaseLogicBlockView
import com.hesheng1024.happystudy.custom.blocks.calculate.LogicBgBlockView
import com.hesheng1024.happystudy.custom.blocks.motion.MoveBlockView

/**
 *
 * @author hesheng1024
 * @date 2020/4/1 11:03
 */
@SuppressLint("ViewConstructor")
class IfBlockView : BaseControlBlockView {

    private val mLogicBgView: LogicBgBlockView

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : this(context, attrs, defStyleAttr, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int)
            : super(context, attrs, defStyleAttr, defStyleRes) {
        mLogicBgView = LogicBgBlockView(context)
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
        mLogicBgView.setBgColorId(R.color.colorControlYellowDark)
        mLogicBgView.tag = ChildTag.TAG_TOP
        // 也可以直接在父类中统一监听，只是坐标计算相对复杂一点
        var isIn = false
        mLogicBgView.setOnDragListener { v, event ->
            when(event.action) {
                DragEvent.ACTION_DRAG_ENTERED -> {
                    LogUtil.i(msg = "logicBgView entered")
                    isIn = true
                }
                DragEvent.ACTION_DRAG_EXITED -> {
                    LogUtil.i(msg = "logicBgView exited")
                    isIn = false
                }
                DragEvent.ACTION_DROP -> {
                    LogUtil.i(msg = "logicBgView drop")
                    val logicBlock = event.localState
                    if (isIn && mLogicBgView.childCount == 0 && logicBlock is BaseLogicBlockView) {
                        (logicBlock.parent as? ViewGroup)?.removeView(logicBlock)
                        mLogicBgView.addView(logicBlock)
                    } else {
                        LogUtil.i(msg = "can't add view: isIn->$isIn count:${mLogicBgView.childCount} logic:$logicBlock")
                    }
                }
            }
            return@setOnDragListener true
        }
        addView(mLogicBgView, lp)

        val tvThen = TextView(context)
        tvThen.setText(R.string.then)
        tvThen.tag = ChildTag.TAG_TOP
        tvThen.setTextColor(whiteColor)
        addView(tvThen)
    }

    override fun onRun(role: IRoleView) {
        if (mLogicBgView.judgeResult()) {
            onChildRun(role)
        }
    }

    override fun clone(): IBaseBlock {
        val newObj = IfBlockView(context)
        newObj.layoutParams = this.layoutParams
        newObj.minimumWidth = measuredWidth
        newObj.minimumHeight = measuredHeight
        val child = mLogicBgView.getChildAt(0)
        if (child != null && child is BaseLogicBlockView) {
            newObj.mLogicBgView.addView(child.clone() as BaseLogicBlockView)
        }
        return newObj
    }
}