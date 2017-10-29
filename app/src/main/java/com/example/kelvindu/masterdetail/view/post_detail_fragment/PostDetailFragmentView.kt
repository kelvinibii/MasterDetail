package com.example.kelvindu.masterdetail.view.post_detail_fragment

import android.app.Activity
import com.example.kelvindu.masterdetail.model.Position
import com.example.kelvindu.masterdetail.model.Post

/**
 * Created by KelvinDu on 10/18/2017.
 */
interface PostDetailFragmentView {
    fun getActivityFromFragment(): Activity
    fun getPostId(): Long
    fun setPost(post: Post)
    fun setPositionViews(position: Position)
}