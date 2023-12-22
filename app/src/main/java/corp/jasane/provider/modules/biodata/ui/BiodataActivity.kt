package corp.jasane.provider.modules.biodata.ui

import android.Manifest
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.maps.model.LatLng
import com.google.android.material.bottomsheet.BottomSheetDialog
import corp.jasane.provider.R
import corp.jasane.provider.appcomponents.utility.DatePickerFragment
import corp.jasane.provider.data.response.Category
import corp.jasane.provider.databinding.ActivityBiodataBinding
import corp.jasane.provider.databinding.ActivityVerificationFirstBinding
import corp.jasane.provider.modules.ViewModelFactory
import corp.jasane.provider.modules.biodata.data.BiodataActivityVM
import corp.jasane.provider.modules.home.ui.HomeActivity
import corp.jasane.provider.modules.home.ui.ui.addJob.AddJobAdapter
import corp.jasane.provider.modules.home.ui.ui.addJob.AddJobFragment
import corp.jasane.provider.modules.home.ui.ui.maps.MapsFragment
import corp.jasane.provider.modules.login.data.viewModel.LoginActivityViewModel
import kotlinx.coroutines.launch
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class BiodataActivity : AppCompatActivity(), DatePickerFragment.DialogDateListener, MapsFragment.OnLocationSelectedListener {

    private lateinit var binding: ActivityBiodataBinding
    private var lat: Double = 0.0
    private var lon: Double = 0.0
    private lateinit var progressDialog: Dialog
    private lateinit var dialog: BottomSheetDialog
    private val viewModel by viewModels<BiodataActivityVM> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBiodataBinding.inflate(layoutInflater)
        setContentView(binding.root)

        progressDialog = Dialog(this)
        progressDialog.setContentView(R.layout.progress_dialog)
        progressDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        progressDialog.setCancelable(false)

        val name = intent.getStringExtra(EXTRA_NAMA)?.uppercase(Locale.getDefault())
        Log.d("id", "$name")
        val nik = intent.getStringExtra(EXTRA_NIK)
        Log.d("id", "$nik")

        binding.nik.text = nik
        binding.fullName.text = name

        binding.pinPointText.setOnClickListener {
            showLoading()
            onPinPointTextClicked(it)
            hideLoading()
        }

        binding.date.setOnClickListener {
            showDatePicker()
        }

        viewModel.insertBiodataSuccess.observe(this) { success ->
//            Toast.makeText(this@BiodataActivity, "insert Biodata Success", Toast.LENGTH_SHORT).show()
            hideLoading()
            if (success) {
                val intent = Intent(this, HomeActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
                finish()
                Toast.makeText(this, "insert Biodata Success", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "insert Biodata failed", Toast.LENGTH_SHORT).show()
            }
        }

        setupView()
        setOnclick()
    }

    fun onPinPointTextClicked(view: View) {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // Jika izin belum diberikan, maka minta izin
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_LOCATION_PERMISSION
            )
        } else {
            // Jika izin sudah diberikan, tampilkan MapsFragment
            showMapsFragment()
        }
    }

    private fun showMapsFragment() {
        // Tampilkan bottom sheet dengan MapsFragment
        val bottomSheet = MapsFragment()
        bottomSheet.setLocationSelectedListener(this)
        bottomSheet.show(supportFragmentManager, bottomSheet.tag)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_LOCATION_PERMISSION -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Jika izin diberikan, tampilkan MapsFragment
                    showMapsFragment()
                } else {
                    Toast.makeText(
                        this,
                        "Location permission denied. Cannot show map.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    override fun onLocationSelected(latLng: LatLng) {
        lat = latLng.latitude
        lon = latLng.longitude
        updatePinPointText(latLng)
    }

    override fun onLocation(latLng: LatLng) {
        lat = latLng.latitude
        lon = latLng.longitude
        updatePinPointText(latLng)
    }

    private fun updatePinPointText(latLng: LatLng) {
        val geocoder = Geocoder(this, Locale.getDefault())
        try {
            val addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1)
            val city = addresses?.firstOrNull()?.getAddressLine(0) ?: "Unknown"
            binding.pinPointText.text = city
        } catch (e: IOException) {
            e.printStackTrace()
        }
        Log.d("updatePinPointText", "$latLng")
    }

    private fun setupView() {
        supportActionBar?.apply {
            title = getString(R.string.title_biodata)
            setDisplayHomeAsUpEnabled(true)
        }
    }

    private fun showDatePicker() {
        val datePickerFragment = DatePickerFragment()
        datePickerFragment.show(supportFragmentManager, "datePicker")
    }

    override fun onDialogDateSet(tag: String?, year: Int, month: Int, dayOfMonth: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(year, month, dayOfMonth)
        val dateFormat = SimpleDateFormat("yyyy/MM/dd", Locale.getDefault())
        binding.date.text = dateFormat.format(calendar.time)
    }

    private fun setOnclick(){
        binding.upload.setOnClickListener {
            showLoading()
            val name = intent.getStringExtra(EXTRA_NAMA)?.uppercase(Locale.getDefault()) ?: ""
            val nik = intent.getStringExtra(EXTRA_NIK) ?: ""
            val birthday = binding.date.text.toString()
            val telephone = binding.noHandphoneEditText.text.toString()
            val province = binding.provinsiEditText.text.toString()
            val city = binding.cityEditText.text.toString()
            val subdistrict = binding.kecamatanEditText.text.toString()
            val village = binding.kelurahanEditText.text.toString()
            val address = binding.addressEditText.text.toString()
            val lat = lat
            val lon = lon

            lifecycleScope.launch {
                viewModel.insertBiodata(nik, name, birthday, telephone, province, city, subdistrict, village, address, lat, lon)
//                val intent = Intent(this@BiodataActivity, HomeActivity::class.java)
//                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
//                startActivity(intent)
//                finish()
            }
        }
//        viewModel.insertBiodataSuccess.observe(this) { success ->
////            Toast.makeText(this@BiodataActivity, "insert Biodata Success", Toast.LENGTH_SHORT).show()
//            hideLoading()
//            if (success) {
//                val intent = Intent(this, HomeActivity::class.java)
//                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
//                startActivity(intent)
//                finish()
//                Toast.makeText(this, "insert Biodata Success", Toast.LENGTH_SHORT).show()
//            } else {
//                Toast.makeText(this, "insert Biodata failed", Toast.LENGTH_SHORT).show()
//            }
//        }
    }

    private fun showLoading() {
        progressDialog.show()
    }

    private fun hideLoading() {
        progressDialog.dismiss()
    }

    companion object{
        const val EXTRA_NAMA = "extra_nama"
        const val EXTRA_NIK = "extra_nik"
        private const val REQUEST_LOCATION_PERMISSION = 123
    }
}