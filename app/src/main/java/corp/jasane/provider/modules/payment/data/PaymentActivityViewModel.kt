package corp.jasane.provider.modules.payment.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import corp.jasane.provider.data.UserRepository
import corp.jasane.provider.data.response.DataInsertPayment
import corp.jasane.provider.data.response.InsertPaymentResponse
import corp.jasane.provider.data.response.PaymentResponse
import corp.jasane.provider.data.retrofit.ApiService
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PaymentActivityViewModel(
    private val apiService: ApiService,
    private val userRepository: UserRepository,
): ViewModel()  {

    private val _paymentDetail = MutableLiveData<PaymentResponse?>()
    val paymentDetail: LiveData<PaymentResponse?> get() = _paymentDetail

    private val _insertPayment = MutableLiveData<DataInsertPayment?>()
    val insertPayment: LiveData<DataInsertPayment?> get() = _insertPayment

    fun init(id: Int) {
        viewModelScope.launch {
            userRepository.getSession().collect {
                if (it.isLogin) {
                    getPaymentById(id, it.access_token)
                    Log.d("token", it.access_token)
                }
            }
        }
    }


    fun getPaymentById(id: Int, authorizationHeader: String) {
        apiService.payment(id, "Bearer $authorizationHeader").enqueue(object : Callback<PaymentResponse> {
            override fun onResponse(call: Call<PaymentResponse>, response: Response<PaymentResponse>) {
                if (response.isSuccessful) {
                    val getWorkResponse = response.body()
                    Log.d("getWorkResponse", "$getWorkResponse")
                    if (getWorkResponse != null) {
                        _paymentDetail.value = getWorkResponse
                        Log.d("_workDetail", "$_paymentDetail")
                    } else {
                    }
                } else {
                    // Handle error
                }
                Log.d("getWorkResponse", "$response")
            }

            override fun onFailure(call: Call<PaymentResponse>, t: Throwable) {
                // Handle failure
                Log.e("API Request", "Failed: ${t.message}", t)
            }
        })
    }

    fun inserPayment(
        offerId: Int,
        paymentMethod: String,
        bidPrice: Int,
        adminFees: Int,
        total: Int
    ) {
        viewModelScope.launch {
            userRepository.getSession().collect {
                if (it.isLogin) {
                    apiService.insertPayment(offerId, paymentMethod, bidPrice.toString(), adminFees, total, "Bearer ${it.access_token}")
                        .enqueue(object : Callback<InsertPaymentResponse> { // Fix the Callback type
                            override fun onResponse(
                                call: Call<InsertPaymentResponse>,
                                response: Response<InsertPaymentResponse>
                            ) {
                                if (response.isSuccessful) {
                                    val insertPaymentResponse = response.body()
                                    Log.d("insertPaymentResponse", "$insertPaymentResponse")
                                    if (insertPaymentResponse != null) {
                                        val dataInsertPayment = insertPaymentResponse.data
                                        _insertPayment.value = dataInsertPayment
                                        Log.d("_insertPayment", "$_insertPayment")
                                    } else {
                                        // Handle null response
                                    }
                                } else {
                                    // Handle error
                                }
                            }

                            override fun onFailure(call: Call<InsertPaymentResponse>, t: Throwable) {
                                // Handle failure
                                Log.e("API Request", "Failed: ${t.message}", t)
                            }
                        })
                }
            }
        }
    }
}