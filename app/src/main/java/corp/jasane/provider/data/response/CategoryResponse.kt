package corp.jasane.provider.data.response

import com.google.gson.annotations.SerializedName

data class CategoryResponse(
    @SerializedName("meta") val meta: MetaCategory,
    @SerializedName("data") val data: List<Category>
)

data class MetaCategory(
    @SerializedName("code") val code: Int,
    @SerializedName("status") val status: String,
    @SerializedName("message") val message: String
)

data class Category(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("slug") val slug: String,
    @SerializedName("created_at") val createdAt: String?,
    @SerializedName("updated_at") val updatedAt: String?,
)