package com.example.ecomama.proses

import android.app.AlertDialog
import com.example.ecomama.LoadingActivity

class LoadingProgress(val mActivity: LoadingActivity) {
    private lateinit var isdialog: AlertDialog
    fun startLoading() {

        val infalter = mActivity.layoutInflater
        val bulider = AlertDialog.Builder(mActivity)

        bulider.setCancelable(false)
        isdialog = bulider.create()
        isdialog.show()

    }

    fun isDismiss() {
        isdialog.dismiss()
    }
}
