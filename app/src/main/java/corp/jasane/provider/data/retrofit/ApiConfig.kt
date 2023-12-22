package corp.jasane.provider.data.retrofit

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiConfig {
    private const val BASE_URL = "https://jasane-vy5bsv56bq-et.a.run.app"

    private val loggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    // Add the logging interceptor to OkHttpClient
    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)  // Set the OkHttpClient with the logging interceptor
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    // Use lazy initialization to create the ApiService instance
    val apiInstant: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}