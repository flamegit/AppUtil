package com.example.flame.kotlinstudy.ui

import android.os.Bundle
import android.provider.SyncStateContract
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import com.example.flame.kotlinstudy.R
import com.example.flame.kotlinstudy.model.Constants

/**
 * Created by Administrator on 2017/2/12.
 */

class ChoseSiteDialog : DialogFragment() {
    var buttons = arrayOfNulls<RadioButton>(3)
    var ok:View?=null
    var cancel:View?=null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.dialog_chose_site, container, false)
        arguments?.let {
            it.getInt(Constants.KEY_SITE_TYPE)
            buttons[0] = view.findViewById(R.id.meizi)
            buttons[1] = view.findViewById(R.id.gank)
            buttons[2] = view.findViewById(R.id.bizi)
        }
        ok=view.findViewById(R.id.ok)
        cancel=view.findViewById(R.id.cancel)
        return view
    }
    companion object {
        fun newInstance(site: Int): ChoseSiteDialog {
            val bundle = Bundle()
            bundle.putInt(Constants.KEY_SITE_TYPE, site)
            val dialog = ChoseSiteDialog()
            dialog.arguments = bundle
            return dialog
        }
    }
}




