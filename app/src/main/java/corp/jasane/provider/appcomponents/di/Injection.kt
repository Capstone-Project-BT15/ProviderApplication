package corp.jasane.provider.appcomponents.di

import android.content.Context
import corp.jasane.provider.data.UserRepository
import corp.jasane.provider.data.pref.UserPreference
import corp.jasane.provider.data.pref.dataStore
import corp.jasane.provider.data.retrofit.ApiConfig
import corp.jasane.provider.data.retrofit.ApiService

object Injection {
    fun provideRepository(context: Context): UserRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        return UserRepository.getInstance(pref)
    }

    fun provideApiService(): ApiService {
        return ApiConfig.apiInstant
    }

}