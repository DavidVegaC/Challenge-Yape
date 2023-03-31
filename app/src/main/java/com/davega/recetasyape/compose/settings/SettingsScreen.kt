package com.davega.recetasyape.compose.settings

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.davega.domain.models.SettingType
import com.davega.domain.models.Settings
import com.davega.recetasyape.R
import com.davega.recetasyape.compose.utils.SnackbarUtils
import com.davega.recetasyape.core.theme.ThemeUtils
import com.davega.recetasyape.core.theme.ThemeUtilsImp
import com.davega.recetasyape.ui.settings.SettingUIModel
import com.davega.recetasyape.ui.settings.SettingsViewModel
import com.google.accompanist.themeadapter.material.MdcTheme

@Composable
fun SettingsTitle(modifier: Modifier = Modifier) {
    Text(
        text = stringResource(id = R.string.menu_settings),
        style = MaterialTheme.typography.body2,
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 18.dp, top = 10.dp)
    )
}

@Composable
fun SettingThemeMode(modifier: Modifier = Modifier, settings: Settings, onClick: (Settings) -> Unit) {
    val checkedState = remember { mutableStateOf(settings.selectedValue) }
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(50.dp)
            .padding(horizontal = 12.dp)
            .padding(vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.weight(1f),
            style = MaterialTheme.typography.body2,
            text = settings.settingLabel
        )
        Switch(
            checked = checkedState.value,
            onCheckedChange = {
                checkedState.value = it
                onClick(settings.apply { selectedValue = checkedState.value})
            }
        )
    }
}

@Composable
fun SettingClearCache(modifier: Modifier = Modifier, settings: Settings, onClick: (Settings) -> Unit) {
    val color = LocalContentColor.current.copy(alpha = LocalContentAlpha.current)
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(50.dp)
            .padding(horizontal = 12.dp)
            .padding(vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        ClickableText(
            modifier = Modifier.fillMaxWidth(),
            style = MaterialTheme.typography.body2.copy(color = color),
            text = AnnotatedString(settings.settingLabel),
            onClick = {onClick(settings)}
        )
    }

}

@Composable
fun SettingAppVersion(modifier: Modifier = Modifier, settings: Settings) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(50.dp)
            .padding(vertical = 6.dp)
            .padding(start = 12.dp, end = 10.dp), verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.weight(1f),
            style = MaterialTheme.typography.body2,
            text = settings.settingLabel
        )
        Text(
            style = MaterialTheme.typography.body2,
            fontWeight = FontWeight.Bold,
            text = settings.settingValue
        )
    }
}

@Composable
fun SettingsContentItems(listSettings: List<Settings>, settingsViewModel: SettingsViewModel = viewModel()){
    Card(
        shape = RoundedCornerShape(12.dp),
        elevation = 5.dp,
        modifier = Modifier.padding(14.dp),
    ) {
        Column {
            SettingThemeMode(settings = listSettings[0], onClick = {setting -> settingsViewModel.setSettings(setting)})
            SettingClearCache(settings = listSettings[1], onClick = {setting -> settingsViewModel.setSettings(setting)})
            SettingAppVersion(settings = listSettings[2])
        }
    }
}

@Composable
fun SettingsContent(
    modifier: Modifier = Modifier,
    settingsViewModel: SettingsViewModel = viewModel(),
    themeUtils: ThemeUtils = ThemeUtilsImp()
) {
    val responseSettings by settingsViewModel.settings.observeAsState()
    settingsViewModel.getSettings()

    Column(modifier = modifier) {
        SettingsTitle()

        when (responseSettings) {
            //is SettingUIModel.Loading -> handleLoading(true)
            is SettingUIModel.Error -> SnackbarUtils((responseSettings as SettingUIModel.Error).error)
            is SettingUIModel.Success -> SettingsContentItems((responseSettings as SettingUIModel.Success).data, settingsViewModel)
            is SettingUIModel.NightMode -> (responseSettings as SettingUIModel.NightMode).nightMode.let { themeUtils.setNightMode(it) }
            else -> {}
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSettingsTitle() {
    MdcTheme {
        SettingsTitle()
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSettingsThemeMode() {
    MdcTheme {
        SettingThemeMode(settings = Settings(1, SettingType.SWITCH, "Theme mode", "", false), onClick = {})
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSettingsClearCache() {
    MdcTheme {
        SettingClearCache(settings = Settings(2, SettingType.EMPTY, "Clear cache", ""), onClick = {})
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSettingsAppVersion() {
    MdcTheme {
        SettingAppVersion(settings = Settings(2, SettingType.EMPTY, "App version", "2.0"))
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewSettingsContent() {
    MdcTheme {
        SettingsContent()
    }
}