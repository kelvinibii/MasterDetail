package com.example.kelvindu.masterdetail.inject.module

import com.example.kelvindu.masterdetail.presenter.post_detail_fragment.PostDetailPresenter
import com.example.kelvindu.masterdetail.presenter.post_detail_fragment.PostDetailPresenterImpl
import dagger.Module
import dagger.Provides

/**
 * Created by KelvinDu on 10/24/2017.
 */
@Module
class PostDetailFragmentModule {
    @Provides
    fun getPostDetailFragmentPresenter(): PostDetailPresenter = PostDetailPresenterImpl()
}