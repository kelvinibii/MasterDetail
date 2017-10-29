package com.example.kelvindu.masterdetail.view.post_list_activity

import android.app.Activity
import com.example.kelvindu.masterdetail.model.Post

/**
 * Created by KelvinDu on 10/18/2017.
 */
interface PostListActivityView {
    fun onLoadPosts(posts: List<Post>)
    fun showProgress(show: Boolean)
    fun showLoadErrorMsg(err: String)
    fun showEmptyView()
}