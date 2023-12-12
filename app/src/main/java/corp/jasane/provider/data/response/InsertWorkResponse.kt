package corp.jasane.provider.data.response

import com.google.gson.annotations.SerializedName

data class InsertWorkResponse(
    @SerializedName("meta") val meta: MetaInsertWork,
    @SerializedName("data") val data: InsertWork
)

data class MetaInsertWork(
    @SerializedName("code") val code: Int,
    @SerializedName("status") val status: String,
    @SerializedName("message") val message: String
)

data class InsertWork(
    @SerializedName("id") val id: Int,
    @SerializedName("image") val image: String,
    @SerializedName("title") val title: String,
    @SerializedName("category_id") val categoryId: String,
    @SerializedName("telephone") val telephone: String?,
    @SerializedName("min_budget") val minBudget: String?,
    @SerializedName("max_budget") val maxBudget: String,
    @SerializedName("type_of_work") val typeOfWork: String,
    @SerializedName("start_date") val startDate: String?,
    @SerializedName("description") val description: String?,
    @SerializedName("latitude") val latitude: String,
    @SerializedName("longitude") val longitude: String,
    @SerializedName("created_at") val createdAt: String?,
    @SerializedName("updated_at") val updatedAt: String?,
)