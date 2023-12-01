package corp.jasane.provider.data.retrofit

import corp.jasane.provider.data.response.LoginResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiService {
    @FormUrlEncoded
    @POST("api/login/recruiter")
    fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<LoginResponse>

//    @GET("api/home/user")
//    fun home(
//        @Header("Authorization") authorization: String
//    ): Call<HomeUserResponse>
//
//    @GET("api/works/{id}")
//    fun detailWork(
//        @Path("id") id: Int,
//        @Header("Authorization") authorization: String
//    ): Call<GetWorkResponse>
}