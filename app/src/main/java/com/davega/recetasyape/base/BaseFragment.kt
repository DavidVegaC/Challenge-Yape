package com.davega.recetasyape.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.davega.recetasyape.core.dialog.dismissLoadingDialog
import com.davega.recetasyape.core.dialog.showLoadingDialog
import com.davega.recetasyape.extension.showSnackBar
import timber.log.Timber

abstract class BaseFragment<VB : ViewBinding, ViewModel : BaseViewModel> : Fragment() {

    protected lateinit var binding: VB
    protected abstract val viewModel: ViewModel

    abstract fun getViewBinding(): VB

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = getViewBinding()
        return binding.root
    }

    protected open fun handleLoading(isLoading: Boolean) {
        if (isLoading) showLoadingDialog() else dismissLoadingDialog()
    }

    protected open fun handleErrorMessage(message: String?) {
        if (message.isNullOrBlank()) return
        dismissLoadingDialog()
        Timber.e(message)
        showSnackBar(binding.root, message)
    }
}