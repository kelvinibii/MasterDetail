package com.example.kelvindu.masterdetail.inject.component

import com.example.kelvindu.masterdetail.inject.module.PostDetailFragmentModule
import com.example.kelvindu.masterdetail.inject.scope.PerActivity
import com.example.kelvindu.masterdetail.view.post_detail_fragment.PostDetailFragment
import dagger.Component

/**
 * Created by KelvinDu on 10/24/2017.
 */
@PerActivity
@Component(modules = arrayOf(PostDetailFragmentModule::class))
interface PostDetailFragmentComponent {
    fun inject(postDetailFragment: PostDetailFragment)
}