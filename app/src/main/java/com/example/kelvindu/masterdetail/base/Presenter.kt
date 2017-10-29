package com.example.kelvindu.masterdetail.base

/**
 * Created by KelvinDu on 10/17/2017.
 */
interface Presenter<in T> {
    fun subscribe()
    fun unSubscribe()
    fun attachView(view : T)
}