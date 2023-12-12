package corp.jasane.provider.data.retrofit

import corp.jasane.provider.data.response.CategoryResponse
import corp.jasane.provider.data.response.InsertWorkResponse
import corp.jasane.provider.data.response.LoginResponse
import corp.jasane.provider.data.response.RegisterResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import java.io.File

interface ApiService {
    @FormUrlEncoded
    @POST("api/login/recruiter")
    fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<LoginResponse>

    @FormUrlEncoded
    @POST("api/register/recruiter")
    fun register(
        @Field("fullname") fullName: String,
        @Field("telephone") telephone: String,
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("password_confirmation") passwordConfirmation: String
    ): Call<RegisterResponse>

    @Multipart
    @POST("api/works")
    suspend fun insertWork(
        @Part image: MultipartBody.Part,
        @Part("title") title: RequestBody,
        @Part("category_id") categoryId: RequestBody,
        @Part("telephone") telephone: RequestBody,
        @Part("min_budget") minBudget: RequestBody,
        @Part("max_budget") maxBudget: RequestBody,
        @Part("type_of_work") typeOfWork: RequestBody,
        @Part("start_date") startDate: RequestBody,
        @Part("description") description: RequestBody,
        @Part("latitude") latitude: RequestBody,
        @Part("longitude") longitude: RequestBody,
        @Header("Authorization") authorization: String
    ): InsertWorkResponse

    @GET("api/categories")
    fun categoryJob(
        @Header("Authorization") authorization: String
    ): Call<CategoryResponse>

//    @GET("api/works/{id}")
//    fun detailWork(
//        @Path("id") id: Int,
//        @Header("Authorization") authorization: String
//    ): Call<GetWorkResponse>
}