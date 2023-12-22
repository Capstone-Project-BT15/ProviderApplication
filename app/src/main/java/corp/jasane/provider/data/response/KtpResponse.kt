package corp.jasane.provider.data.response

import com.google.gson.annotations.SerializedName

data class KtpResponse(
    @SerializedName("error") val error: String,
    @SerializedName("message") val message: String,
    @SerializedName("NIK") val nik: String,
    @SerializedName("Nama") val nama: String,
    @SerializedName("Tgl Lahir") val tglLahir: String,
    @SerializedName("Ling Photo") val photo: String,
)