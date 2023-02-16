package com.davega.recetasyape.ui.settings

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.davega.recetasyape.base.BaseFragment
import com.davega.recetasyape.base.BaseViewModel
import com.davega.recetasyape.core.theme.ThemeUtils
import com.davega.recetasyape.databinding.FragmentSettingsBinding
import com.davega.recetasyape.extension.observe
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SettingsFragment : BaseFragment<FragmentSettingsBinding, BaseViewModel>() {

    override fun getViewBinding(): FragmentSettingsBinding =
        FragmentSettingsBinding.inflate(layoutInflater)

    override val viewModel: SettingsViewModel by viewModels()

    @Inject
    lateinit var settingsAdapter: SettingsAdapter

    @Inject
    lateinit var themeUtils: ThemeUtils

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observe(viewModel.settings, ::onViewStateChange)
        setupRecyclerView()
        viewModel.getSettings()
    }

    private fun setupRecyclerView() {
        binding.recyclerViewSettings.apply {
            adapter = settingsAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        settingsAdapter.setItemClickListener { selectedSetting ->
            viewModel.setSettings(selectedSetting)
        }
    }

    private fun onViewStateChange(result: SettingUIModel) {
        if (result.isRedelivered) return
        when (result) {
            is SettingUIModel.Error -> handleErrorMessage(result.error)
            is SettingUIModel.Loading -> handleLoading(true)
            is SettingUIModel.NightMode -> {
                result.nightMode.let {
                    themeUtils.setNightMode(it)
                }
            }
            is SettingUIModel.Success -> {
                handleLoading(false)
                result.data.let {
                    settingsAdapter.list = it
                }
            }
        }
    }
}