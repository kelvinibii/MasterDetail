package com.example.kelvindu.masterdetail.presenter.post_detail_fragment

import com.example.kelvindu.masterdetail.base.Presenter
import com.example.kelvindu.masterdetail.view.post_detail_fragment.PostDetailFragmentView

/**
 * Created by KelvinDu on 10/18/2017.
 */
interface PostDetailPresenter : Presenter<PostDetailFragmentView> {
    fun getLocation()
    fun loadDetailPost(postId: Long)
    fun loadPosition(postId: Long)
}