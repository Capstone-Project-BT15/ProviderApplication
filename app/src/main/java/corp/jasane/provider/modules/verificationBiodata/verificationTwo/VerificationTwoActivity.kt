package corp.jasane.provider.modules.verificationBiodata.verificationTwo

import android.content.ContentValues.TAG
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.airbnb.lottie.LottieAnimationView
import com.google.common.util.concurrent.ListenableFuture
import corp.jasane.provider.R
import corp.jasane.provider.databinding.ActivityHomeBinding
import corp.jasane.provider.databinding.ActivityVerificationTwoBinding
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import android.Manifest
import android.content.Intent
import corp.jasane.provider.modules.biodata.ui.BiodataActivity

class VerificationTwoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityVerificationTwoBinding
    private lateinit var cameraExecutor: ExecutorService
    private lateinit var cameraProviderFuture: ListenableFuture<ProcessCameraProvider>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVerificationTwoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        cameraExecutor = Executors.newSingleThreadExecutor()
        cameraProviderFuture = ProcessCameraProvider.getInstance(this)

        binding.lottieViewCross.setAnimation(R.raw.cross)
        binding.lottieViewCheck.setAnimation(R.raw.ic_check)

        checkCameraPermission()
        setOnclick()
        setupView()

    }

    private fun setupView() {
        supportActionBar?.apply {
            title = getString(R.string.title_scan_ktp)
            setDisplayHomeAsUpEnabled(true)
        }
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)

        cameraProviderFuture.addListener({
            val cameraProvider = cameraProviderFuture.get()

            val preview = Preview.Builder().build()
            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(
                    this, cameraSelector, preview
                )

                val previewView: PreviewView = binding.cameraPreview
                preview.setSurfaceProvider(previewView.surfaceProvider)

            } catch (exc: Exception) {
                Log.e(TAG, "Error starting camera", exc)
            }
        }, ContextCompat.getMainExecutor(this))
    }
    override fun onDestroy() {
        super.onDestroy()
        cameraProviderFuture.addListener({
            val cameraProvider = cameraProviderFuture.get()
            cameraProvider.unbindAll()
        }, ContextCompat.getMainExecutor(this))
        cameraExecutor.shutdown()
    }

    private fun checkCameraPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_DENIED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.CAMERA),
                REQUEST_CAMERA_PERMISSION_CODE
            )
        } else {
            startCamera()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            REQUEST_CAMERA_PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startCamera()
                } else {
                    Toast.makeText(
                        this,
                        "Izin kamera ditolak. Aplikasi tidak dapat menggunakan kamera.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            else -> {
                super.onRequestPermissionsResult(requestCode, permissions, grantResults)
            }
        }
    }

    private fun setOnclick(){
        binding.buttonSave.setOnClickListener {
            val intent = Intent(this, BiodataActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
    companion object {
        private const val REQUEST_CAMERA_PERMISSION_CODE = 1001
    }
}