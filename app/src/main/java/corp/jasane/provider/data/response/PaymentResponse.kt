package corp.jasane.provider.data.response

import com.google.gson.annotations.SerializedName

data class PaymentResponse(
    @SerializedName("meta") val meta: MetaPayment,
    @SerializedName("data") val data: PaymentData
)

data class MetaPayment(
    @SerializedName("code") val code: Int,
    @SerializedName("status") val status: String,
    @SerializedName("message") val message: String
)

data class PaymentData(
    @SerializedName("id") val id: Int,
    @SerializedName("user_id") val userId: Int,
    @SerializedName("work_id") val workId: Int,
    @SerializedName("address_id") val addressId: Int,
    @SerializedName("tariff") val tariff: String,
    @SerializedName("experience") val experience: String,
    @SerializedName("status") val status: String,
    @SerializedName("is_rating") val isRating: Int,
    @SerializedName("created_at") val createdAt: String,
    @SerializedName("updated_at") val updatedAt: String,
    @SerializedName("admin_fees") val adminFees: Int,
    @SerializedName("total") val total: Int,
    @SerializedName("ratings") val ratings: RatingsUser,
    @SerializedName("address") val address: Address
)

data class RatingsUser(
    @SerializedName("user_id") val userId: Int,
    @SerializedName("star") val star: String,
    @SerializedName("count") val count: Int
)

data class Address(
    @SerializedName("id") val id: Int,
    @SerializedName("user_id") val userId: Int,
    @SerializedName("fullname") val fullname: String,
    @SerializedName("telephone") val telephone: String,
    @SerializedName("province") val province: String,
    @SerializedName("city") val city: String,
    @SerializedName("subdistrict") val subdistrict: String,
    @SerializedName("village") val village: String,
    @SerializedName("address") val address: String,
    @SerializedName("latitude") val latitude: String,
    @SerializedName("longitude") val longitude: String,
    @SerializedName("status") val status: String,
    @SerializedName("created_at") val createdAt: String,
    @SerializedName("updated_at") val updatedAt: String
)
