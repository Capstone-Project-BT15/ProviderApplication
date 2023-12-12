package corp.jasane.provider.modules.verificationBiodata.verificationFirst.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import corp.jasane.provider.R
import corp.jasane.provider.databinding.ActivityVerificationFirstBinding
import corp.jasane.provider.modules.verificationBiodata.verificationTwo.VerificationTwoActivity

class VerificationFirstActivity : AppCompatActivity() {

    private lateinit var binding: ActivityVerificationFirstBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVerificationFirstBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar

        binding.lottieViewCheck.setAnimation(R.raw.ic_check)
        binding.buttonSave.setOnClickListener {
            val intent = Intent(this, VerificationTwoActivity::class.java)
            intent.flags =
                Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            finish()
        }

        setupView()
    }

    private fun setupView() {
        supportActionBar?.apply {
            title = getString(R.string.verification_identity)
            setDisplayHomeAsUpEnabled(true)
        }
    }
}