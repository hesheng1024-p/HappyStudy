package com.hesheng1024.happystudy.modules.study.v

import com.hesheng1024.base.base.IBaseView
import com.hesheng1024.happystudy.modules.Block

/**
 *
 * @author hesheng1024
 * @date 2020/3/19 10:03
 */
interface IStudyMotionView : IBaseView {

    fun setBlocks(blocks: List<Block>)
}