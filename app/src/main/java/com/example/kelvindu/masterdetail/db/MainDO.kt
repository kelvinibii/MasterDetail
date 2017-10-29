package com.example.kelvindu.masterdetail.db

import com.example.kelvindu.masterdetail.model.Position
import com.example.kelvindu.masterdetail.model.Post
import io.reactivex.Observable

/**
 * Created by KelvinDu on 10/17/2017.
 */
interface MainDO {
    fun insertPost(feed: Post): Boolean
    fun insertPosition(feed: Position): Boolean
    fun updatePosition(position: Position)
    fun loadPost(): Observable<List<Post>>
    fun loadPosition(post: Long): Observable<Position>
    fun loadById(id: Long): Observable<Post>
    fun getCount():Int
    fun checkAvailablePosition(post_id: Long): Boolean
}