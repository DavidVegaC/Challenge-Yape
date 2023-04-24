package com.davega.recetasyape.ui.viewmodel.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.davega.domain.interactor.GetSettingsUseCase
import com.davega.domain.models.Settings
import com.davega.recetasyape.base.BaseViewModel
import com.davega.recetasyape.utils.CoroutineContextProvider
import com.davega.recetasyape.utils.PresentationPreferencesHelper
import com.davega.recetasyape.utils.UiAwareModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import javax.inject.Inject

sealed class SettingUIModel : UiAwareModel() {
    object Loading : SettingUIModel()
    data class Error(var error: String = "") : SettingUIModel()
    data class Success(val data: List<Settings>) : SettingUIModel()
    data class NightMode(val nightMode: Boolean) : SettingUIModel()
}

@HiltViewModel
class SettingsViewModel @Inject constructor(
    contextProvider: CoroutineContextProvider,
    private val getSettingsUseCase: GetSettingsUseCase,
    private val preferencesHelper: PresentationPreferencesHelper
) : BaseViewModel(contextProvider) {

    private val _settings = MutableLiveData<SettingUIModel>()
    val settings: LiveData<SettingUIModel> = _settings

    override val coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
        _settings.postValue(SettingUIModel.Error(exception.message ?: "Error"))
    }

    fun getSettings() {
        _settings.postValue(SettingUIModel.Loading)
        launchCoroutineIO {
            loadSettings()
        }
    }

    private suspend fun loadSettings() {
        getSettingsUseCase(preferencesHelper.isNightMode).collect {
            _settings.postValue(SettingUIModel.Success(it))
        }
    }

    fun setSettings(selectedSetting: Settings) {
        selectedSetting.run {
            preferencesHelper.isNightMode = selectedValue
            _settings.postValue(SettingUIModel.NightMode(selectedValue))
        }
    }
}