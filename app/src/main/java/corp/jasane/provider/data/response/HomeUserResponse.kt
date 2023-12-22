package corp.jasane.provider.data.response

import com.google.gson.annotations.SerializedName

data class HomeUserResponse(
    @SerializedName("meta") val meta: MetaHome,
    @SerializedName("data") val data: HomeUserData
)

data class MetaHome(
    @SerializedName("code") val code: Int,
    @SerializedName("status") val status: String,
    @SerializedName("message") val message: String
)

data class HomeUserData(
    @SerializedName("works") val closestWork: List<WorkDetail>,
)

data class WorkDetail(
    @SerializedName("id") val id: Int,
    @SerializedName("image") val image: String,
    @SerializedName("title") val title: String,
    @SerializedName("category_id") val categoryId: Int,
    @SerializedName("telephone") val telephone: String,
    @SerializedName("min_budget") val minBudget: String,
    @SerializedName("max_budget") val maxBudget: String,
    @SerializedName("type_of_work") val typeOfWork: String,
    @SerializedName("start_date") val startDate: String,
    @SerializedName("description") val description: String,
    @SerializedName("latitude") val latitude: String,
    @SerializedName("longitude") val longitude: String,
    @SerializedName("created_at") val createdAt: String,
    @SerializedName("updated_at") val updatedAt: String,
    @SerializedName("distance") val distance: Double,
    @SerializedName("distance_to_user") val distanceToUser: String
)