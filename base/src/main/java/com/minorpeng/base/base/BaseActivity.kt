package com.minorpeng.base.base

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.minorpeng.base.R
import com.minorpeng.base.utils.LogUtil
import com.minorpeng.base.utils.ToastUtil

/**
 *
 * @author MinorPeng
 * @date 2020/2/8 10:48
 */
abstract class BaseActivity<P : BasePresenter<out IBaseView, out IBaseModel>> : AppCompatActivity(),
    IBaseView {

    protected var mTranslucent = isTranslucent()
    protected lateinit var mContentView: View
    protected lateinit var mPresenter: P

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        if (mTranslucent) {
            setTranslucent()
        }
        setContentView(inflateContentView())
        mPresenter = createPresenter()
        initView()
    }

    protected open fun inflateContentView(): View {
        mContentView = View.inflate(this, getLayoutId(), null)
        return mContentView
    }

    override fun toastMsg(msg: String) {
        ToastUtil.show(this, msg)
    }

    override fun finishActivity() {
        finish()
    }

    override fun noMore() {}

    /**
     * 获取布局id
     * @return layout_block_edit_text id
     */
    protected abstract fun getLayoutId(): Int

    /**
     * 创建presenter
     * @return presenter
     */
    protected abstract fun createPresenter(): P

    /**
     * 初始化
     */
    protected abstract fun initView()

    /**
     * 获取状态栏高度
     * @return int statusHeight
     */
    protected fun getStatusBarHeight(): Int {
        var statusHeight = 0
        val resourceId =
            this.resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) { //还可以通过反射获取
            statusHeight = this.resources.getDimensionPixelOffset(resourceId)
        }
        LogUtil.d("statusH:$statusHeight")
        //不知道为什么，获取的高度是实际的高度的2倍 视觉上是这样
        return statusHeight / 2
    }

    protected fun getActionBarHeight(): Float {
        var actionBarHeight = 0
        val tv = TypedValue()
        if (theme.resolveAttribute(R.attr.actionBarSize, tv, true)) {
            //方法一
            actionBarHeight =
                TypedValue.complexToDimensionPixelSize(tv.data, resources.displayMetrics)
            //方法二
            //int[] attribute = new int[] { android.R.attr.actionBarSize };
            //TypedArray array = obtainStyledAttributes(tv.resourceId, attribute);
            //int actionBarHeight1 = array.getDimensionPixelSize(0 /* index */, -1 /* default size */);
            //array.recycle();
            //
            //方法三
            //TypedArray actionbarSizeTypedArray = obtainStyledAttributes(new int[] { android.R.attr.actionBarSize });
            //float actionBarHeight2 = actionbarSizeTypedArray.getDimension(0, 0);
            //actionbarSizeTypedArray.recycle();
        }
        return actionBarHeight.toFloat()
    }

    /**
     * 是否透明状态栏
     * @return boolean
     */
    protected open fun isTranslucent(): Boolean {
        return true
    }

    protected open fun getStatusBarBackground(): Int {
        return R.drawable.bg_toolbar
    }

    private fun setTranslucent() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = window
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_LAYOUT_STABLE)
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = Color.TRANSPARENT
        }/* else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        }*/
    }
}