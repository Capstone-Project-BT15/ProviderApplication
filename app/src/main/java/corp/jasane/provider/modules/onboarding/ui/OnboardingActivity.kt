package corp.jasane.provider.modules.onboarding.ui

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.content.ContentProviderCompat.requireContext
import corp.jasane.provider.R
import corp.jasane.provider.appcomponents.base.BaseActivity
import corp.jasane.provider.databinding.ActivityOnboardingBinding
import corp.jasane.provider.modules.home.ui.HomeActivity
import corp.jasane.provider.modules.home.ui.ui.home.HomeFragment
import corp.jasane.provider.modules.login.ui.LoginActivity
import corp.jasane.provider.modules.onboarding.`data`.viewmodel.OnboardingVM
import kotlin.String
import kotlin.Unit

class OnboardingActivity : BaseActivity<ActivityOnboardingBinding>(R.layout.activity_onboarding) {
  private val viewModel: OnboardingVM by viewModels<OnboardingVM>()

  private lateinit var progressDialog: Dialog

  override fun onInitialized(): Unit {
    viewModel.navArguments = intent.extras?.getBundle("bundle")

    if (isOnboardingCompleted()) {
      startActivity(Intent(this, HomeActivity::class.java))
      finish()
    } else {
      binding.onboardingVM = viewModel
    }
    setupView()
  }

  private fun setupView() {
    supportActionBar?.hide()
  }

  override fun setUpClicks(): Unit {
    binding.btnMulai.setOnClickListener {
      saveOnboardingStatus()
      startActivity(Intent(this, LoginActivity::class.java))
      finish()
    }
  }

  private fun saveOnboardingStatus() {
    val sharedPreferences: SharedPreferences =
      getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
    val editor: SharedPreferences.Editor = sharedPreferences.edit()
    editor.putBoolean("onBoarding", true)
    editor.apply()
  }

  private fun isOnboardingCompleted(): Boolean {
    val sharedPreferences: SharedPreferences =
      getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
    return sharedPreferences.getBoolean("onBoarding", false)
  }

//  private fun showLoading() {
//    progressDialog.show()
//  }

//  private fun hideLoading() {
//    progressDialog.dismiss()
//  }


  companion object {
    const val TAG: String = "ONBOARDING_ACTIVITY"


    fun getIntent(context: Context, bundle: Bundle?): Intent {
      val destIntent = Intent(context, OnboardingActivity::class.java)
      destIntent.putExtra("bundle", bundle)
      return destIntent
    }
  }
}