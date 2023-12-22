package corp.jasane.provider.data.response

data class BiodataResponse(
    val meta: MetaBiodata,
    val data: UserDataBiodata
)

data class MetaBiodata(
    val code: Int,
    val status: String,
    val message: String
)

data class UserDataBiodata(
    val user: UserBiodata,
    val address: AddressBiodata
)

data class UserBiodata(
    val id: Int,
    val photo: String,
    val nik: String,
    val fullname: String,
    val birthday: String,
    val telephone: String,
    val email: String,
    val email_verified_at: String?,
    val role: String,
    val is_online: String,
    val created_at: String,
    val updated_at: String
)

data class AddressBiodata(
    val user_id: Int,
    val fullname: String,
    val telephone: String,
    val province: String,
    val city: String,
    val subdistrict: String,
    val village: String,
    val address: String,
    val latitude: String,
    val longitude: String,
    val updated_at: String,
    val created_at: String,
    val id: Int
)