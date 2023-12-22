package corp.jasane.provider.modules.biodata.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import corp.jasane.provider.data.UserRepository
import corp.jasane.provider.data.response.BiodataResponse
import corp.jasane.provider.data.retrofit.ApiService
import kotlinx.coroutines.launch
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BiodataActivityVM(
    private val apiService: ApiService,
    private val userRepository: UserRepository)
    : ViewModel() {

    private val _insertBiodataSuccess = MutableLiveData<Boolean>()
    val insertBiodataSuccess: LiveData<Boolean> get() = _insertBiodataSuccess

    fun insertBiodata(
        nik: String,
        fullName: String,
        birthday: String,
        telephone: String,
        province: String,
        city: String,
        subDistrict: String,
        village: String,
        address: String,
        lat: Double,
        lon: Double
    ) {
        viewModelScope.launch {
            userRepository.getSession().collect {
                if (it.isLogin) {
                    val nikRequestBody = nik.toRequestBody(okhttp3.MultipartBody.FORM)
                    val fullNameRequestBody = fullName.toRequestBody(okhttp3.MultipartBody.FORM)
                    val birthdayRequestBody = birthday.toRequestBody(okhttp3.MultipartBody.FORM)
                    val telephoneRequestBody = telephone.toRequestBody(okhttp3.MultipartBody.FORM)
                    val provinceRequestBody = province.toRequestBody(okhttp3.MultipartBody.FORM)
                    val cityRequestBody = city.toRequestBody(okhttp3.MultipartBody.FORM)
                    val subDistrictRequestBody =
                        subDistrict.toRequestBody(okhttp3.MultipartBody.FORM)
                    val villageRequestBody = village.toRequestBody(okhttp3.MultipartBody.FORM)
                    val addressRequestBody = address.toRequestBody(okhttp3.MultipartBody.FORM)
                    val latRequestBody = lat.toString().toRequestBody(okhttp3.MultipartBody.FORM)
                    val lonRequestBody = lon.toString().toRequestBody(okhttp3.MultipartBody.FORM)
                    apiService.insertBiodata(
                        nikRequestBody,
                        fullNameRequestBody,
                        birthdayRequestBody,
                        telephoneRequestBody,
                        provinceRequestBody,
                        cityRequestBody,
                        subDistrictRequestBody,
                        villageRequestBody,
                        addressRequestBody,
                        latRequestBody,
                        lonRequestBody,
                        "Bearer ${it.access_token}"
                    )
                        .enqueue(object : Callback<BiodataResponse> {
                            override fun onResponse(
                                call: Call<BiodataResponse>,
                                response: Response<BiodataResponse>
                            ) {
                                _insertBiodataSuccess.value = response.isSuccessful
                                Log.d("offerJobviewmodel", "$response")
                            }

                            override fun onFailure(call: Call<BiodataResponse>, t: Throwable) {
                                // Handle failure
                                Log.e("API Request", "Failed: ${t.message}", t)
                            }
                        })
                    Log.d("insertBiodataViewmodel", "$nik, $fullName, $birthday, $telephone, $province, $city, $subDistrict, $village, $lat, $lon, ${it.access_token}")
                }
            }
        }
    }
}