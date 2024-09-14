package com.firstapp.moviemaker.data.storage

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "movie-maker-store")

class MovieMakerStore(private val context: Context, private val initial: Int) {

    val budget: Flow<Int> = context.dataStore.data
        .map { preferences -> preferences[budgetKey] ?: initial }

    suspend fun incrementBudget(newBudget: Int) {
        context.dataStore.edit { preferences ->
            val previousBudget = preferences[budgetKey] ?: initial
            preferences[budgetKey] = previousBudget + newBudget
        }
    }

    companion object {
        private val budgetKey = intPreferencesKey("budget")
    }
}