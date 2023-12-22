package corp.jasane.provider.data.retrofit

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiConfigOCR {
    private const val BASE_URL = "https://ocr-ml-vy5bsv56bq-et.a.run.app"
    //local
//    "https://ocr-ml-vy5bsv56bq-et.a.run.app"

    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(120, TimeUnit.SECONDS) // Set the connection timeout
        .readTimeout(120, TimeUnit.SECONDS)    // Set the read timeout
        .writeTimeout(120, TimeUnit.SECONDS)   // Set the write timeout
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val apiInstant = retrofit.create(ApiServiceOCR::class.java)
}