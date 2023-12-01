package corp.jasane.provider.data.pref

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.preferencesDataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "session")

class UserPreference private constructor(private val dataStore: DataStore<Preferences>) {

    suspend fun saveSession(user: UserModel) {
        dataStore.edit { preferences ->
//            preferences[USERID_KEY] = user.userId
//            preferences[NAME_KEY] = user.name
            preferences[ACCESS_TOKEN] = user.access_token
            preferences[IS_LOGIN_KEY] = true
        }
    }

    fun getSession(): Flow<UserModel> {
        return dataStore.data.map { preferences ->
            UserModel(
//                preferences[USERID_KEY] ?: "",
//                preferences[NAME_KEY] ?: "",
                preferences[ACCESS_TOKEN] ?: "",
                preferences[IS_LOGIN_KEY] ?: false
            )
        }
    }

//    suspend fun getToken(): String {
//        return dataStore.data.map { preferences ->
//            preferences[ACCESS_TOKEN] ?: ""
//        }.first()
//    }

    suspend fun logout() {
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: UserPreference? = null

        //        private val NAME_KEY = stringPreferencesKey("email")
        private val ACCESS_TOKEN = stringPreferencesKey("access_token")
        //        private val USERID_KEY = stringPreferencesKey("userId")
        private val IS_LOGIN_KEY = booleanPreferencesKey("isLogin")

        fun getInstance(dataStore: DataStore<Preferences>): UserPreference {
            return INSTANCE ?: synchronized(this) {
                val instance = UserPreference(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }


}