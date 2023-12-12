package corp.jasane.provider.modules.biodata.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import corp.jasane.provider.R
import corp.jasane.provider.databinding.ActivityBiodataBinding
import corp.jasane.provider.databinding.ActivityVerificationFirstBinding
import corp.jasane.provider.modules.home.ui.HomeActivity

class BiodataActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBiodataBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBiodataBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        setOnclick()
    }

    private fun setupView() {
        supportActionBar?.apply {
            title = getString(R.string.title_biodata)
            setDisplayHomeAsUpEnabled(true)
        }
    }

    private fun setOnclick(){
        binding.upload.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}