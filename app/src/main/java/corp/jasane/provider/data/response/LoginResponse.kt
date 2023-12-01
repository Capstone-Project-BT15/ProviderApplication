package corp.jasane.provider.data.response

data class LoginResponse(
    val message: String,
    val access_token: String,
    val token_type: String
)