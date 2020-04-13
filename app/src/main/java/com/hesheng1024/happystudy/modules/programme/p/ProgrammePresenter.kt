package com.hesheng1024.happystudy.modules.programme.p

import android.content.Context
import com.hesheng1024.base.base.BasePresenter
import com.hesheng1024.base.utils.LogUtil
import com.hesheng1024.happystudy.modules.programme.m.IProgrammeModel
import com.hesheng1024.happystudy.modules.programme.m.impl.ProgrammeModel
import com.hesheng1024.happystudy.modules.programme.v.IProgrammeView

/**
 *
 * @author hesheng1024
 * @date 2020/4/7 20:24
 */
class ProgrammePresenter(programmeView: IProgrammeView) : BasePresenter<IProgrammeView, IProgrammeModel>(programmeView) {

    override fun createModel(): IProgrammeModel {
        return ProgrammeModel()
    }

    fun getBlocks(context: Context) {
        val blocks = mModel.initBlocks(context)
        if (blocks.isNullOrEmpty()) {
            LogUtil.e(msg = "init blocks error!")
            mView.toastMsg("something error!")
        } else {
            mView.setBlocks(blocks)
        }
    }
}