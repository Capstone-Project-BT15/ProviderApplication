package corp.jasane.provider.modules.home.ui.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import corp.jasane.provider.data.UserRepository
import corp.jasane.provider.data.pref.UserModel
import corp.jasane.provider.data.response.HomeUserResponse
import corp.jasane.provider.data.response.WorkDetail
import corp.jasane.provider.data.retrofit.ApiService
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragmentViewModel(private val userRepository: UserRepository, private val apiService: ApiService) : ViewModel() {

    private val _workDetails = MutableLiveData<List<WorkDetail>>()
    val workDetails: LiveData<List<WorkDetail>> get() = _workDetails

    init {
        viewModelScope.launch {
            userRepository.getSession().collect {
                if (it.isLogin) {
                    fetchWorkDetails(it.access_token)
                    Log.d("token", it.access_token)
                }
            }
        }
    }

    private fun fetchWorkDetails(authorizationHeader: String) {
        apiService.home("Bearer $authorizationHeader").enqueue(object : Callback<HomeUserResponse> {
            override fun onResponse(call: Call<HomeUserResponse>, response: Response<HomeUserResponse>) {
                if (response.isSuccessful) {
                    val homeUserResponse = response.body()
                    Log.d("homeUserResponse", "$homeUserResponse")
                    val closestWork = homeUserResponse?.data?.closestWork.orEmpty()
                    Log.d("closestWork", "$closestWork")
                    _workDetails.value = closestWork
                } else {
                    // Handle error
                }
            }

            override fun onFailure(call: Call<HomeUserResponse>, t: Throwable) {
                // Handle failure
                Log.e("API Request", "Failed: ${t.message}", t)
            }
        })
    }

    fun getSession(): LiveData<UserModel> {
        return userRepository.getSession().asLiveData()
    }
}