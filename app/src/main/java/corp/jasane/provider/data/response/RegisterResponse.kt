package corp.jasane.provider.data.response

import com.google.gson.annotations.SerializedName

data class RegisterResponse(
    @SerializedName("data")
    val data: userRegister,

    @SerializedName("access_token")
    val access_token: String,

    @SerializedName("token_type")
    val token_type: String
)

data class userRegister(
    @SerializedName("id") val id: Int,
    @SerializedName("fullname") val fullName: String,
    @SerializedName("telephone") val telephone: String,
    @SerializedName("email") val email: String,
    @SerializedName("role") val role: String,
    @SerializedName("created_at") val createdAt: String,
    @SerializedName("updated_at") val updatedAt: String,
)