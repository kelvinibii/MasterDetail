package com.example.kelvindu.masterdetail.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import timber.log.Timber

/**
 * Created by KelvinDu on 10/18/2017.
 */
@Suppress("UNCHECKED_CAST")
abstract class RecyclerViewAdapter<T, VH: RecyclerView.ViewHolder>
(protected var layout: Int, internal var viewHolderClass: Class<VH>, internal var modelClass: Class<T>, internal var datas: List<T>):
RecyclerView.Adapter<VH>() {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): VH {
        val view = LayoutInflater.from(parent!!.context).inflate(layout,parent,false) as ViewGroup
        try {
            val constructor = viewHolderClass.getConstructor(View::class.java)
            return constructor.newInstance(view)
        } catch (e: Exception) {
            Timber.e(e)
            throw e
        }
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val model = datas[position]
        bindView(holder, model)
    }

    override fun getItemCount(): Int =datas.size

    protected abstract fun bindView(holder: VH, model: T)
}