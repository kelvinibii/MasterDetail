package com.example.kelvindu.masterdetail.presenter.post_list

import com.example.kelvindu.masterdetail.base.Presenter
import com.example.kelvindu.masterdetail.model.Post
import com.example.kelvindu.masterdetail.view.post_list_activity.PostListActivityView

/**
 * Created by KelvinDu on 10/18/2017.
 */
interface PostListPresenter: Presenter<PostListActivityView> {
    fun feedData()
    fun loadDb()
    fun onFeedSuccess(res: List<Post>)
}