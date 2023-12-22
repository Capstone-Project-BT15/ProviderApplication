package corp.jasane.provider.data.response

data class OffersRecruiterResponse(
    val meta: Meta,
    val data: Data
)

data class Meta(
    val code: Int,
    val status: String,
    val message: String
)

data class Ratings(
    val user_id: Int?,
    val work_id: Int?,
    val star: String?,
    val count: Int?
)

data class WorkItem(
    val work_id: Int,
    val work_title: String,
    val work_image: String,
    val data: List<UserData>
)

data class UserData(
    val user_fullname: String,
    val work_image: String,
    val id: Int,
    val user_id: Int,
    val address_id: Int,
    val tariff: String,
    val experience: String,
    val status: String,
    val is_rating: Int,
    val created_at: String,
    val updated_at: String,
    val ratings: Ratings?,
    val distance: String
)

data class Data(
    val pending: List<WorkItem>,
    val accepted: List<WorkItem>,
    val finished: List<WorkItem>
)