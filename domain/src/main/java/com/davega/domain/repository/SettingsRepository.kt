package com.davega.domain.repository

import com.davega.domain.models.Settings
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    suspend fun getSettings(isNightMode: Boolean): Flow<List<Settings>>
}
