package com.example.kelvindu.masterdetail.inject.module

import com.example.kelvindu.masterdetail.presenter.post_list.PostListPresenter
import com.example.kelvindu.masterdetail.presenter.post_list.PostListPresenterImpl
import dagger.Module
import dagger.Provides

/**
 * Created by KelvinDu on 10/18/2017.
 */
@Module
class PostListModule {
    @Provides
    fun getPostListPresenter(): PostListPresenter = PostListPresenterImpl()
}