package corp.jasane.provider.modules.home.ui.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import corp.jasane.provider.data.UserRepository
import corp.jasane.provider.data.pref.UserModel
import corp.jasane.provider.data.retrofit.ApiService
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel (private val userRepository: UserRepository, private val apiService: ApiService): ViewModel(){

//    private val _workDetails = MutableLiveData<List<WorkDetail>>()
//    val workDetails: LiveData<List<WorkDetail>> get() = _workDetails

//    init {
//        viewModelScope.launch {
//            userRepository.getSession().collect {
//                if (it.isLogin) {
//                    fetchWorkDetails(it.access_token)
//                }
//            }
//        }
//    }

//    private fun fetchWorkDetails(authorizationHeader: String) {
//        apiService.home("Bearer $authorizationHeader").enqueue(object : Callback<HomeUserResponse> {
//            override fun onResponse(call: Call<HomeUserResponse>, response: Response<HomeUserResponse>) {
//                if (response.isSuccessful) {
//                    val homeUserResponse = response.body()
//                    val allWorksWithDistance = homeUserResponse?.data?.allWorksWithDistance.orEmpty()
//                    _workDetails.value = allWorksWithDistance
//                } else {
//                    // Handle error
//                }
//            }
//
//            override fun onFailure(call: Call<HomeUserResponse>, t: Throwable) {
//                // Handle failure
//                Log.e("API Request", "Failed: ${t.message}", t)
//            }
//        })
//    }

    fun getSession(): LiveData<UserModel> {
        return userRepository.getSession().asLiveData()
    }

//    private val _text = MutableLiveData<String>().apply {
//        value = "This is home Fragment"
//    }
//    val text: LiveData<String> = _text
//
//    private val _jobItemList = MutableLiveData<List<JobItem>>()
//    val jobItemList: LiveData<List<JobItem>> get() = _jobItemList
//
//    init {
//        // Populate the job items (replace this with your actual data source)
//        val items = listOf(
//            JobItem("Job 1", "1005 kilometer", "Details 1", "Budget 1", "Rp100,000", "Rp120,000"),
//            JobItem("Job 2", "10 km", "Details 2", "Budget 2", "Rp150,000", "Rp180,000"),
//            JobItem("Job 3", "10 km", "Details 3", "Budget 2", "Rp1500,000", "Rp180,000"),
//            JobItem("Job 42143", "10 km", "Details 4", "Budget 2", "Rp150,000", "Rp180,000"),
//            JobItem("Job 5", "5 km", "Details 5", "Budget 1", "Rp100,000", "Rp120,000"),
//            JobItem("Job 6", "10 km", "Details 6", "Budget 2", "Rp150,000", "Rp180,000"),
//            JobItem("Job 7", "10 km", "Details 7", "Budget 2", "Rp150,000", "Rp180,000"),
//            JobItem("Job 8", "10 km", "Details 8", "Budget 2", "Rp1,250,000", "Rp1,800,000"),
//            JobItem("Job 9", "5 km", "Details 9", "Budget 1", "Rp100,000", "Rp120,000"),
//            JobItem("Job 10", "10 km", "Details 10", "Budget 2", "Rp150,000", "Rp1,800,000"),
//            JobItem("Job 11", "10 km", "Details 11", "Budget 2", "Rp150,000", "Rp180,000"),
//            JobItem("Job 12", "10 km", "Details 12", "Budget 2", "Rp150,000", "Rp180,000"),
//            // Add more items as needed
//        )
//        _jobItemList.value = items
//    }
}