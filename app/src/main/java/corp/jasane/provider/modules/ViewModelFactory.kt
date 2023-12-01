package corp.jasane.provider.modules

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import corp.jasane.provider.appcomponents.di.Injection
import corp.jasane.provider.data.UserRepository
import corp.jasane.provider.data.retrofit.ApiService
import corp.jasane.provider.modules.home.ui.ui.home.HomeViewModel
import corp.jasane.provider.modules.login.data.viewModel.LoginActivityViewModel
import corp.jasane.provider.modules.signup.data.viewModel.SignupActivityViewModel

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
//            modelClass.isAssignableFrom(HomeActivityViewModel::class.java) -> {
//                HomeActivityViewModel(userRepository) as T
//            }
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                HomeViewModel(userRepository, apiService) as T
            }
//            modelClass.isAssignableFrom(DetailJobViewModel::class.java) -> {
//                DetailJobViewModel(apiService, userRepository) as T
//            }
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