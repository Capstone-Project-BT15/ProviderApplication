package corp.jasane.provider.modules.home.ui.ui.addJob

import android.util.Log
import androidx.lifecycle.*
import com.google.gson.Gson
import corp.jasane.provider.data.UserRepository
import corp.jasane.provider.data.response.CategoryResponse
import corp.jasane.provider.data.response.InsertWorkResponse
import corp.jasane.provider.data.retrofit.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response
import java.io.File

class AddJobViewModel(
    private val apiService: ApiService,
    private val userRepository: UserRepository,
    ) : ViewModel() {

    private val _categoryJob = MutableLiveData<CategoryResponse?>()
    val detailCategory: LiveData<CategoryResponse?> get() = _categoryJob

    private val _selectedJob = MutableLiveData<String>()
    val selectedJob: LiveData<String> get() = _selectedJob

    init {
        viewModelScope.launch(Dispatchers.IO) {
            userRepository.getSession().collect {
                if (it.isLogin) {
                    getCategory(it.access_token)
                }
            }
        }
    }

    fun getCategory(authorizationHeader: String){
        apiService.categoryJob("Bearer $authorizationHeader")
            .enqueue(object : Callback<CategoryResponse> {
                override fun onResponse(call: Call<CategoryResponse>, response: Response<CategoryResponse>) {
                    if (response.isSuccessful) {
                        val getCategoryResponse = response.body()
                        if (getCategoryResponse != null){
                            _categoryJob.value = getCategoryResponse
                        }else{
                            Log.d("Not Success", "$response")
                        }
                        }
                    }

                override fun onFailure(call: Call<CategoryResponse>, t: Throwable) {
                    Log.e("API Request", "Failed: ${t.message}", t)
                }
            })
    }

    private val _insertWorkResponse = MutableLiveData<InsertWorkResponse?>()
    val insertWorkResponse: LiveData<InsertWorkResponse?> get() = _insertWorkResponse

    private val _errorResponse = MutableLiveData<String>()
    val errorResponse: LiveData<String> get() = _errorResponse

    fun uploadWork(
        imageJob: File,
        title: String,
        categoryId: Int,
        telephone: String,
        minBudget: String,
        maxBudget: String,
        typeOfWork: String,
        startDate: String,
        description: String,
        lat: String,
        lon: String,
        onSuccess: (InsertWorkResponse) -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch() {
            userRepository.getSession().collect {
                try {
                    if (it.isLogin) {
                        val requestImageFile = imageJob.asRequestBody("image/jpeg".toMediaType())
                        val imagePart = MultipartBody.Part.createFormData(
                            "image",
                            imageJob.name,
                            requestImageFile
                        )

                        val titlePart = title.toRequestBody("text/plain".toMediaType())
                        val categoryIdPart = categoryId.toString().toRequestBody("text/plain".toMediaType())
                        val telephonePart = telephone.toRequestBody("text/plain".toMediaType())
                        val minBudgetPart = minBudget.toRequestBody("text/plain".toMediaType())
                        val maxBudgetPart = maxBudget.toRequestBody("text/plain".toMediaType())
                        val typeOfWorkPart = typeOfWork.toRequestBody("text/plain".toMediaType())
                        val startDatePart = startDate.toRequestBody("text/plain".toMediaType())
                        val descriptionPart = description.toRequestBody("text/plain".toMediaType())
                        val latPart = lat.toRequestBody("text/plain".toMediaType())
                        val lonPart = lon.toRequestBody("text/plain".toMediaType())

                        try {
                            val successResponse = apiService.insertWork(
                                imagePart,
                                titlePart,
                                categoryIdPart,
                                telephonePart,
                                minBudgetPart,
                                maxBudgetPart,
                                typeOfWorkPart,
                                startDatePart,
                                descriptionPart,
                                latPart,
                                lonPart,
                                "Bearer ${it.access_token}"
                            )
                            onSuccess(successResponse)
                            Log.d("onSuccess", "$onSuccess")
                        } catch (e: HttpException) {
                            val errorBody = e.response()?.errorBody()?.string()
                            val errorResponse = Gson().fromJson(errorBody, InsertWorkResponse::class.java)
                            onError(errorResponse?.meta?.message ?: "Unknown error")
                        }
                        Log.d(
                            "cekdataLAig",
                            "$imageJob, $title, $categoryId, $telephone, $minBudget, $maxBudget, $typeOfWork, $startDate, $description, $lat, $lon, Bearer ${it.access_token}"
                        )
                    }
                } catch (e: HttpException) {
                    val errorBody = e.response()?.errorBody()?.string()
                    val errorResponse = Gson().fromJson(errorBody, InsertWorkResponse::class.java)

                    withContext(Dispatchers.Main) {
                        onError(errorResponse.meta.message)
                    }
                }
            }
        }
    }
}