package corp.jasane.provider.modules.register.data.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import corp.jasane.provider.data.UserRepository
import corp.jasane.provider.data.pref.UserModel
import corp.jasane.provider.data.response.RegisterResponse
import corp.jasane.provider.data.retrofit.ApiService
import kotlinx.coroutines.launch
import retrofit2.Call

class RegisterActivityViewModel(
    private val userRepository: UserRepository,
    private val apiService: ApiService
) : ViewModel()
{
    fun register(telephone: String, email: String, password: String, passwordConfirmation: String): Call<RegisterResponse> {
        return apiService.register(telephone, email, password, passwordConfirmation)
    }


    fun saveSession(user: UserModel) {
        viewModelScope.launch {
            userRepository.saveSession(user)
        }
    }
}