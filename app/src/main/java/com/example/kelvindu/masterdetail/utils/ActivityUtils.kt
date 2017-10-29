package com.example.kelvindu.masterdetail.utils

import android.app.Fragment
import android.app.FragmentManager
import android.content.Context
import android.widget.Toast

/**
 * Created by KelvinDu on 10/17/2017.
 */
object ActivityUtils {
    fun addFragmentToActivity(fragmentManager: FragmentManager,
                              fragment: Fragment,
                              frameId:Int, fragmentTag: String){
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(frameId,fragment,fragmentTag).addToBackStack("")
        transaction.commit()
    }

    fun makeToast(context: Context,message: String, len: Int) =
            Toast.makeText(context, message, len).show()
}