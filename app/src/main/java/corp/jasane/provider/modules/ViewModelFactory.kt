package corp.jasane.provider.modules

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import corp.jasane.provider.appcomponents.di.Injection
import corp.jasane.provider.data.UserRepository
import corp.jasane.provider.data.retrofit.ApiService
import corp.jasane.provider.modules.home.ui.ui.addJob.AddJobViewModel
import corp.jasane.provider.modules.home.ui.ui.home.HomeFragmentViewModel
import corp.jasane.provider.modules.home.ui.ui.profile.ProfileViewModel
import corp.jasane.provider.modules.login.data.viewModel.LoginActivityViewModel
import corp.jasane.provider.modules.register.data.viewModel.RegisterActivityViewModel

class ViewModelFactory(
    private val userRepository: UserRepository,
    private val apiService: ApiService,
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(LoginActivityViewModel::class.java) -> {
                LoginActivityViewModel(userRepository, apiService) as T
            }
            modelClass.isAssignableFrom(AddJobViewModel::class.java) -> {
                AddJobViewModel(apiService, userRepository) as T
            }
            modelClass.isAssignableFrom(HomeFragmentViewModel::class.java) -> {
                HomeFragmentViewModel(userRepository) as T
            }
            modelClass.isAssignableFrom(RegisterActivityViewModel::class.java) -> {
                RegisterActivityViewModel(userRepository, apiService) as T
            }
            modelClass.isAssignableFrom(ProfileViewModel::class.java) -> {
                ProfileViewModel(userRepository, apiService) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null
        @JvmStatic
        fun getInstance(context: Context): ViewModelFactory {
            if (INSTANCE == null) {
                synchronized(ViewModelFactory::class.java) {
                    INSTANCE = ViewModelFactory(
                        Injection.provideRepository(context),
                        Injection.provideApiService(),
                    )
                }
            }
            return INSTANCE as ViewModelFactory
        }
    }
}