package com.davega.recetasyape.extension

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.davega.recetasyape.R
import com.google.android.material.snackbar.Snackbar

internal fun Activity.showSnackBar(view: View, message: String) {
    Snackbar.make(view, message, Snackbar.LENGTH_LONG).apply {
        anchorView = view.rootView.findViewById(R.id.bottomNavigationView)
        show()
    }
}

internal fun Fragment.showSnackBar(view: View, message: String) {
    Snackbar.make(view, message, Snackbar.LENGTH_LONG).apply {
        anchorView = view.rootView.findViewById(R.id.bottomNavigationView)
        show()
    }
}

internal fun Context.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}