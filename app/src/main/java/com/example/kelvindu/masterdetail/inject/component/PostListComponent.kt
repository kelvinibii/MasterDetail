package com.example.kelvindu.masterdetail.inject.component

import com.example.kelvindu.masterdetail.inject.module.PostListModule
import com.example.kelvindu.masterdetail.inject.scope.PerActivity
import com.example.kelvindu.masterdetail.view.post_list_activity.PostListActivity
import dagger.Component

/**
 * Created by KelvinDu on 10/18/2017.
 */
@PerActivity
@Component(modules = arrayOf(PostListModule::class))
interface PostListComponent {
    fun inject(postListActivity: PostListActivity)
}