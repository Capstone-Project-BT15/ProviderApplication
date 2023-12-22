package corp.jasane.provider.modules.login.data.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import corp.jasane.provider.data.UserRepository
import corp.jasane.provider.data.pref.UserModel
import corp.jasane.provider.data.response.LoginResponse
import corp.jasane.provider.data.retrofit.ApiService
import kotlinx.coroutines.launch
import retrofit2.Call

class LoginActivityViewModel (
    private val userRepository: UserRepository,
    private val apiService: ApiService
) : ViewModel() {

    fun login(email: String, password: String): Call<LoginResponse> {
        return apiService.login(email, password)
    }

    fun saveSession(user: UserModel) {
        viewModelScope.launch {
            userRepository.saveSession(user)
        }
    }
}