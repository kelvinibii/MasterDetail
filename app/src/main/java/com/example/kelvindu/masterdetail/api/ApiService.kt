package com.example.kelvindu.masterdetail.api

import com.example.kelvindu.masterdetail.BuildConfig
import com.example.kelvindu.masterdetail.model.Post
import io.reactivex.Observable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

/**
 * Created by KelvinDu on 10/17/2017.
 */
class ApiService {

    val service: ApiServiceInterface

    init {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(logging)

        val retrofit = Retrofit.Builder()
                .baseUrl(BuildConfig.URI)
                .addConverterFactory(MoshiConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(httpClient.build())
                .build()

        service = retrofit.create(ApiServiceInterface::class.java)
    }

    fun loadPosts(): Observable<List<Post>> = service.getPosts()
}