package com.example.kelvindu.masterdetail.api

import com.example.kelvindu.masterdetail.model.Post
import io.reactivex.Observable
import retrofit2.http.GET

/**
 * Created by KelvinDu on 10/17/2017.
 */
interface ApiServiceInterface {
    @GET("users/1/posts")
    fun getPosts(): Observable<List<Post>>
}