package corp.jasane.provider.data.response

import com.google.gson.annotations.SerializedName

data class OfferFinishedResponse(
    @SerializedName("meta") val meta: MetaOfferFinished,
    @SerializedName("data") val data: DataOfferFinished
)

data class MetaOfferFinished(
    @SerializedName("code") val code: Int,
    @SerializedName("status") val status: String,
    @SerializedName("message") val message: String
)

data class DataOfferFinished(
    @SerializedName("offer_id") val offerId: Int,
    @SerializedName("id") val id: Int,
    @SerializedName("payment_method") val paymentMethod: String,
    @SerializedName("bid_price") val bidPrice: Double,
    @SerializedName("admin_fees") val adminFees: String,
    @SerializedName("total") val total: String,
    @SerializedName("updated_at") val updatedAt: String,
    @SerializedName("created_at") val createdAt: String
)