package com.purple.aicalendar.core.utils

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.purple.aicalendar.BuildConfig
import com.purple.aicalendar.domain.models.Event
import kotlinx.coroutines.flow.first
import kotlinx.serialization.json.Json
import javax.inject.Inject
import javax.inject.Singleton
@Singleton
class AICalendarPreference @Inject constructor(private val cont: Context) {
    /**
     * The name of the DataStore preferences file.
     * Using the application ID ensures the preference file is uniquely named for this app.
     */
     val preferenceName = BuildConfig.APPLICATION_ID
    /**
     * Extension property on [Context] to provide a singleton instance of [DataStore] for Preferences.
     *
     * This uses the `preferencesDataStore` delegate, which ensures that there's only one
     * instance of DataStore with the name [preferenceName] in the application.
     */
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = preferenceName)
    private object Keys {
        val KEPT_LIST = stringPreferencesKey("kept_list")
        val DISCARDED_LIST = stringPreferencesKey("discarded_list")
        val PREDICTION_LIST = stringPreferencesKey("prediction_list")
    }

    suspend fun saveList(kept:List<Event>,discarded: List<Event>,predictions: List<Event>) {
        val keptJson = Json.encodeToString(kept)
        val discardedJson = Json.encodeToString(discarded)
        val predictionJson = Json.encodeToString(predictions)
        Log.d("AICalendarPreference", "saveList: $keptJson,$discardedJson,$predictionJson")
        cont.dataStore.edit { prefs ->
            prefs[Keys.KEPT_LIST] = keptJson
        }
        cont.dataStore.edit { prefs ->
            prefs[Keys.DISCARDED_LIST] = discardedJson
        }
        cont.dataStore.edit { prefs ->
            prefs[Keys.PREDICTION_LIST] = predictionJson
        }

    }

    suspend fun getKeptList(): List<Event> {

        val json = cont.dataStore.data.first()[Keys.KEPT_LIST]
        Log.d("AICalendarPreference", "getKeptList: $json")
        return if (json.isNullOrEmpty()) {
            emptyList()
        } else {
            Json.decodeFromString(json)
        }
    }
    suspend fun getDiscardList(): List<Event> {

        val json = cont.dataStore.data.first()[Keys.DISCARDED_LIST]

        Log.d("AICalendarPreference", "getDiscardedList: $json")
        return if (json.isNullOrEmpty()) {
            emptyList()
        } else {
            Json.decodeFromString(json)
        }
    }
    suspend fun getPredictionList(): List<Event> {

        val json = cont.dataStore.data.first()[Keys.PREDICTION_LIST]
        Log.d("AICalendarPreference", "getPredictionList: $json")
        return if (json.isNullOrEmpty()) {
            emptyList()
        } else {
            Json.decodeFromString(json)
        }
    }
}