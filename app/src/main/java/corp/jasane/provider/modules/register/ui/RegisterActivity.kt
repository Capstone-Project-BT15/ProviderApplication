package corp.jasane.provider.modules.register.ui

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import corp.jasane.provider.databinding.ActivityRegisterBinding
import corp.jasane.provider.modules.ViewModelFactory
import corp.jasane.provider.modules.login.ui.LoginActivity
import corp.jasane.provider.R
import corp.jasane.provider.data.pref.UserModel
import corp.jasane.provider.data.response.RegisterResponse
import corp.jasane.provider.modules.home.ui.HomeActivity
import corp.jasane.provider.modules.register.data.viewModel.RegisterActivityViewModel
import corp.jasane.provider.modules.verificationBiodata.verificationFirst.ui.VerificationFirstActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {

    private val viewModel by viewModels<RegisterActivityViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var progressDialog: Dialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        progressDialog = Dialog(this)
        progressDialog.setContentView(R.layout.progress_dialog)
        progressDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        progressDialog.setCancelable(false)

//        Handler(Looper.getMainLooper()).postDelayed({
//            startActivity(Intent(this, LoginActivity::class.java))
//        }, 4000)

        setupAction()
    }
    private fun setupAction() {
        binding.signupButton.setOnClickListener {
            showLoading()
            val telephone = binding.phoneEditText.text.toString()
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()
            val passwordConfirmation = binding.confirmPasswordEditText.text.toString()
            viewModel.register(telephone, email, password, passwordConfirmation)
                .enqueue(object : Callback<RegisterResponse> {
                    override fun onResponse(call: Call<RegisterResponse>, response: Response<RegisterResponse>) {
                        if (response.isSuccessful) {
                            val registerResponse = response.body()
                            if (registerResponse != null) {
                                hideLoading()

                                val user = UserModel(registerResponse.access_token)
                                viewModel.saveSession(user)
                                AlertDialog.Builder(this@RegisterActivity).apply {
                                    setTitle(R.string.yeah)
                                    setMessage(R.string.berhasil_register)
                                    val intent = Intent(context, VerificationFirstActivity::class.java)
                                    intent.flags =
                                        Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                                    startActivity(intent)
                                    finish()
                                    create()
                                    show()
                                    Toast.makeText(
                                        this@RegisterActivity,
                                        R.string.berhasil_login,
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            } else {
                                AlertDialog.Builder(this@RegisterActivity).apply {
                                    setTitle(R.string.gagal_login)
                                    setMessage(R.string.username_password_salah)
                                    setPositiveButton(R.string.oke) { _, _ ->
                                    }
                                    create()
                                    show()
                                }
                                Toast.makeText(this@RegisterActivity, R.string.gagal_login, Toast.LENGTH_SHORT).show()
                            }
                        }
                        Log.d("registerViewModel","${viewModel.register(email, telephone, password, passwordConfirmation)}")
                    }

                    override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                        AlertDialog.Builder(this@RegisterActivity).apply {
                            setTitle(R.string.gagal_login)
                            setMessage(R.string.gagal_memuat_data)
                            setPositiveButton(R.string.oke) { _, _ ->
                            }
                            create()
                            show()
                        }
                    }
                })
        }

        binding.textLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        setupView()
    }

    private fun setupView() {
        supportActionBar?.hide()
    }

    private fun showLoading() {
        progressDialog.show()
    }

    private fun hideLoading() {
        progressDialog.dismiss()
    }
}