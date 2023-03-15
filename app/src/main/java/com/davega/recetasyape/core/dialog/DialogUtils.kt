package com.davega.recetasyape.core.dialog

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.davega.recetasyape.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder

/**
 * show single loading dialog
 */
var loadingDialog: Dialog? = null

fun Fragment.showLoadingDialog(
    cancelable: Boolean = false,
    canceledOnTouchOutside: Boolean = false
): AlertDialog? {
    return MaterialAlertDialogBuilder(context ?: return null).apply {
        setView(R.layout.layout_loading_dialog)
    }.create().let { dialog ->
        dialog.setCancelable(cancelable)
        dialog.setCanceledOnTouchOutside(canceledOnTouchOutside)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        if (loadingDialog?.isShowing == true) {
            loadingDialog?.dismiss()
        }
        lifecycle.addObserver(object : LifecycleObserver {
            fun onDestroy() {
                dialog.dismiss()
                if (loadingDialog === dialog) {
                    loadingDialog = null
                }
            }
        })
        loadingDialog = dialog
        dialog.show()
        dialog
    }
}

fun dismissLoadingDialog() {
    if (loadingDialog?.isShowing == true) {
        loadingDialog?.dismiss()
    }
}