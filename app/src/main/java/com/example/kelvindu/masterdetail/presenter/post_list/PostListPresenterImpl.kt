package com.example.kelvindu.masterdetail.presenter.post_list

import com.example.kelvindu.masterdetail.api.ApiService
import com.example.kelvindu.masterdetail.db.MainDOImpl
import com.example.kelvindu.masterdetail.model.Post
import com.example.kelvindu.masterdetail.view.post_list_activity.PostListActivityView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

/**
 * Created by KelvinDu on 10/18/2017.
 */
class PostListPresenterImpl:PostListPresenter {

    private val disposable = CompositeDisposable()
    private lateinit var view: PostListActivityView

    override fun feedData() {
        val observablePosts = ApiService().loadPosts()
        val subscription =  observablePosts
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onFeedSuccess, {t: Throwable? ->
                    view.showProgress(false)
                })

        disposable.add(subscription)
    }

    override fun loadDb() {
        val observablePosts = MainDOImpl().loadPost()
        val subscribe = observablePosts
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({res: List<Post> ->
                    for (r in res)
                        Timber.d("%s is in the game", r.title)
                    view.onLoadPosts(res)
                },{t: Throwable? ->
                    view.showProgress(false)
                })
        disposable.add(subscribe)
    }

    override fun onFeedSuccess(res: List<Post>) {
        for (r in res){
            val insert = MainDOImpl().insertPost(r)
            if (insert)
                Timber.d("%s : %s susccesufly entered", r.id, r.title)
            else
                Timber.e("%s : failed entered", r.id)

            view.onLoadPosts(res)
            view.showProgress(true)
        }
    }

    override fun subscribe() {
        val count = MainDOImpl().getCount()
        if (count <= 0) {
            feedData()
            view.showProgress(false)
        } else {
            loadDb()
            view.showProgress(true)
        }
    }

    override fun unSubscribe() {
        disposable.clear()
    }
    override fun attachView(view: PostListActivityView) {
        this.view = view
    }

}