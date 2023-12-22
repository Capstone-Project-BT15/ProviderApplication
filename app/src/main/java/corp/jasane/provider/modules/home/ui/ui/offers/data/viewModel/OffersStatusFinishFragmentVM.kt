package corp.jasane.provider.modules.home.ui.ui.offers.data.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import corp.jasane.provider.data.UserRepository
import corp.jasane.provider.data.response.OffersRecruiterResponse
import corp.jasane.provider.data.response.WorkItem
import corp.jasane.provider.data.retrofit.ApiService
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OffersStatusFinishFragmentVM(private val userRepository: UserRepository, private val apiService: ApiService): ViewModel() {

    private val _workDetails = MutableLiveData<List<WorkItem>>()
    val workDetails: LiveData<List<WorkItem>> get() = _workDetails

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
        apiService.offersRecruiter("Bearer $authorizationHeader").enqueue(object :
            Callback<OffersRecruiterResponse> {
            override fun onResponse(call: Call<OffersRecruiterResponse>, response: Response<OffersRecruiterResponse>) {
                if (response.isSuccessful) {
                    val homeUserResponse = response.body()
                    Log.d("homeUserResponse", "$homeUserResponse")
                    val closestWork = homeUserResponse?.data?.finished.orEmpty()
                    Log.d("closestWork", "$closestWork")
                    _workDetails.value = closestWork
                } else {

                }
            }

            override fun onFailure(call: Call<OffersRecruiterResponse>, t: Throwable) {
                // Handle failure
                Log.e("API Request", "Failed: ${t.message}", t)
            }
        })
    }
}