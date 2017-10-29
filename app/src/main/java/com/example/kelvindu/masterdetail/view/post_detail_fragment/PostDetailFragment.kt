package com.example.kelvindu.masterdetail.view.post_detail_fragment

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.kelvindu.masterdetail.R
import com.example.kelvindu.masterdetail.inject.component.DaggerPostDetailFragmentComponent
import com.example.kelvindu.masterdetail.inject.module.PostDetailFragmentModule
import com.example.kelvindu.masterdetail.model.Position
import com.example.kelvindu.masterdetail.model.Post
import com.example.kelvindu.masterdetail.presenter.post_detail_fragment.PostDetailPresenter
import kotlinx.android.synthetic.main.post_detail.*
import javax.inject.Inject

/**
 * A fragment representing a single Post detail screen.
 * This fragment is either contained in a [PostListActivity]
 * in two-pane mode (on tablets) or a [PostDetailActivity]
 * on handsets.
 */
class PostDetailFragment: Fragment(), PostDetailFragmentView {
    @Inject
    lateinit var presenter: PostDetailPresenter
    private var mItem: Post? = null
    private var mPosition: Position? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DaggerPostDetailFragmentComponent.builder()
                .postDetailFragmentModule(PostDetailFragmentModule())
                .build().inject(this)

        presenter.attachView(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.post_detail, container, false)

        if (arguments.containsKey(ARG_ITEM_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            presenter.loadDetailPost(arguments[PostDetailFragment.ARG_ITEM_ID] as Long)
            presenter.loadPosition(arguments[PostDetailFragment.ARG_ITEM_ID] as Long)
        }
        presenter.subscribe()

        return rootView
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fragment_detail_button.setOnClickListener({ v ->
//            presenter.startConnection()
            presenter.getLocation()
        })
    }

    override fun setPost(post: Post) {
        mItem = post
        mItem?.let {
            fragment_detail_title.text = it.title
        }
    }

    override fun onPause() {
        super.onPause()
        presenter.unSubscribe()
    }

    @SuppressLint("SetTextI18n")
    override fun setPositionViews(position: Position) {
        mPosition = position
        fragment_detail_target.text = "Latitude: "+position.latitude+"\nLongitude: "+position.longitude+"\nTime Start: "+position.timestamp
    }

    override fun getActivityFromFragment(): Activity = this.activity
    override fun getPostId() = arguments[PostDetailFragment.ARG_ITEM_ID] as Long

    companion object {
        /**
         * The fragment argument representing the item ID that this fragment
         * represents.
         */
        const val ARG_ITEM_ID = "item_id"
    }
}
