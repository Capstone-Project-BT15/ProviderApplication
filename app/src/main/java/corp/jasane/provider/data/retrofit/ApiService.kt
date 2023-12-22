package corp.jasane.provider.data.retrofit

import corp.jasane.provider.data.response.BiodataResponse
import corp.jasane.provider.data.response.CategoryResponse
import corp.jasane.provider.data.response.HomeUserResponse
import corp.jasane.provider.data.response.InsertPaymentResponse
import corp.jasane.provider.data.response.InsertWorkResponse
import corp.jasane.provider.data.response.KtpResponse
import corp.jasane.provider.data.response.LoginResponse
import corp.jasane.provider.data.response.OfferFinishedResponse
import corp.jasane.provider.data.response.OffersRecruiterResponse
import corp.jasane.provider.data.response.PaymentResponse
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
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path
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

    @GET("api/offers/recruiter")
    fun offersRecruiter(
        @Header("Authorization") authorization: String
    ): Call<OffersRecruiterResponse>

    @GET("api/home/recruiter")
    fun home(
        @Header("Authorization") authorization: String
    ): Call<HomeUserResponse>

    @GET("api/payments/{id}")
    fun payment(
        @Path("id") id: Int,
        @Header("Authorization") authorization: String
    ): Call<PaymentResponse>

    @FormUrlEncoded
    @POST("api/payments")
    fun insertPayment(
        @Field("offer_id") offerId: Int,
        @Field("payment_method") paymentMethod: String,
        @Field("bid_price") bidPrice: String,
        @Field("admin_fees") adminFees: Int,
        @Field("total") total: Int,
        @Header("Authorization") authorization: String
    ): Call<InsertPaymentResponse>
    @Multipart
    @POST("api/biodata")
    fun insertBiodata(
        @Part("nik") nik: RequestBody,
        @Part("fullname") fullName: RequestBody,
        @Part("birthday") birthday: RequestBody,
        @Part("telephone") telephone: RequestBody,
        @Part("province") province: RequestBody,
        @Part("city") city: RequestBody,
        @Part("subdistrict") subDistrict: RequestBody,
        @Part("village") village: RequestBody,
        @Part("address") address: RequestBody,
        @Part("latitude") lat: RequestBody,
        @Part("longitude") lon: RequestBody,
        @Header("Authorization") authorization: String
    ): Call<BiodataResponse>

    @PUT("api/offers/finished/{id}")
    fun putOfferFinished(
        @Path("id") id: Int,
        @Header("Authorization") authorization: String
    ): Call<OfferFinishedResponse>
}