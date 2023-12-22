package corp.jasane.provider.modules.home.ui.ui.offers.data.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import corp.jasane.provider.data.UserRepository
import corp.jasane.provider.data.response.DataOfferFinished
import corp.jasane.provider.data.response.InsertPaymentResponse
import corp.jasane.provider.data.response.OfferFinishedResponse
import corp.jasane.provider.data.response.OffersRecruiterResponse
import corp.jasane.provider.data.response.WorkItem
import corp.jasane.provider.data.retrofit.ApiService
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OffersStatusAcceptedFragmentVM(private val userRepository: UserRepository, private val apiService: ApiService): ViewModel() {

    private val _workDetails = MutableLiveData<List<WorkItem>>()
    val workDetails: LiveData<List<WorkItem>> get() = _workDetails

    private val _offerFinished = MutableLiveData<DataOfferFinished?>()
    val offerFinished: LiveData<DataOfferFinished?> get() = _offerFinished

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
                    val closestWork = homeUserResponse?.data?.accepted.orEmpty()
                    Log.d("closestWork", "$closestWork")
                    _workDetails.value = closestWork
                } else {
                    // Handle error
                }
            }

            override fun onFailure(call: Call<OffersRecruiterResponse>, t: Throwable) {
                // Handle failure
                Log.e("API Request", "Failed: ${t.message}", t)
            }
        })
    }

    fun offerFinished(
        Id: Int,
    ) {
        viewModelScope.launch {
            userRepository.getSession().collect {
                if (it.isLogin) {
                    apiService.putOfferFinished(Id,"Bearer ${it.access_token}")
                        .enqueue(object : Callback<OfferFinishedResponse> {
                            override fun onResponse(
                                call: Call<OfferFinishedResponse>,
                                response: Response<OfferFinishedResponse>
                            ) {
                                if (response.isSuccessful) {
                                    val offerFinishedResponse = response.body()
                                    Log.d("offerFinishedResponse", "$offerFinishedResponse")
                                    if (offerFinishedResponse != null) {
                                        val dataInsertPayment = offerFinishedResponse.data
                                        _offerFinished.value = dataInsertPayment
                                        Log.d("_insertPayment", "$_offerFinished")
                                    } else {
                                        // Handle null response
                                    }
                                } else {
                                    // Handle error
                                }
                            }

                            override fun onFailure(call: Call<OfferFinishedResponse>, t: Throwable) {
                                // Handle failure
                                Log.e("API Request", "Failed: ${t.message}", t)
                            }
                        })
                }
            }
        }
    }
}