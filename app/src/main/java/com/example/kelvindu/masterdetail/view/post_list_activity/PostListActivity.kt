package com.example.kelvindu.masterdetail.view.post_list_activity

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.example.kelvindu.masterdetail.R
import com.example.kelvindu.masterdetail.adapter.PostViewHolder
import com.example.kelvindu.masterdetail.adapter.RecyclerViewAdapter

import com.example.kelvindu.masterdetail.inject.component.DaggerPostListComponent
import com.example.kelvindu.masterdetail.inject.module.PostListModule
import com.example.kelvindu.masterdetail.model.Post
import com.example.kelvindu.masterdetail.presenter.post_list.PostListPresenter
import com.example.kelvindu.masterdetail.utils.ActivityUtils
import com.example.kelvindu.masterdetail.view.post_detail_activity.PostDetailActivity
import com.example.kelvindu.masterdetail.view.post_detail_fragment.PostDetailFragment
import kotlinx.android.synthetic.main.activity_post_list.*
import kotlinx.android.synthetic.main.post_list.*
import kotlinx.android.synthetic.main.post_list_content.view.*
import javax.inject.Inject

/**
 * An activity representing a list of Pings. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a [PostDetailActivity] representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
class PostListActivity : AppCompatActivity(), PostListActivityView {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private var mTwoPane: Boolean = false
    @Inject
    lateinit var presenter: PostListPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_list)
        injectDependency()

        setSupportActionBar(toolbar)
        toolbar.title = title

        if (post_detail_container != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true
        }
        progressbar.visibility = View.VISIBLE
        presenter.attachView(this)
        presenter.subscribe()

//        ActivityUtils.addFragmentToActivity(fragmentManager,PostListFragment(), R.id.frameLayout,"Post List")
//        setupRecyclerView(post_list)
    }

    private fun injectDependency(){
        val postListFragment = DaggerPostListComponent.builder()
                .postListModule(PostListModule())
                .build()
        postListFragment.inject(this)
    }

    override fun onBackPressed() {
        if (fragmentManager.backStackEntryCount > 1)
            fragmentManager.popBackStack()
        else super.onBackPressed()
    }

    override fun onLoadPosts(posts: List<Post>) {
        val adapter = object: RecyclerViewAdapter<Post, PostViewHolder>(R.layout.post_list_content, PostViewHolder::class.java, Post::class.java, posts) {
            override fun bindView(holder: PostViewHolder, model: Post) {
                holder.itemView.id_text.text = model.id.toString()
                holder.itemView.content.text = model.title

                holder.itemView.setOnClickListener({ v ->
                    if (mTwoPane) {
                        val fragment = PostDetailFragment().apply {
                            arguments = Bundle()
                            arguments.putLong(PostDetailFragment.ARG_ITEM_ID, model.id)
                        }

                        supportFragmentManager.beginTransaction()
                                .replace(R.id.post_detail_container, fragment)
                                .commit()

                    } else {
                        val intent = Intent(v.context, PostDetailActivity::class.java).apply {
                            putExtra(PostDetailFragment.ARG_ITEM_ID, model.id)
                        }
                        v.context.startActivity(intent)
                    }

                })
            }
        }
        post_list.adapter = adapter
    }

    override fun showProgress(show: Boolean) {
        if (show)
            progressbar!!.visibility = View.GONE
        else progressbar!!.visibility = View.VISIBLE
    }

    override fun showLoadErrorMsg(err: String) {
        ActivityUtils.makeToast(this, err, Toast.LENGTH_LONG)
    }

    override fun showEmptyView() {
        ActivityUtils.makeToast(this, "No items", Toast.LENGTH_LONG)
    }

}
