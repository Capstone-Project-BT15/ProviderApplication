package corp.jasane.provider.modules.verificationBiodata.verificationTwo

import android.Manifest
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.util.Size
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.google.common.util.concurrent.ListenableFuture
import corp.jasane.provider.R
import corp.jasane.provider.appcomponents.utility.reduceFileImageKtp
import corp.jasane.provider.databinding.ActivityVerificationTwoBinding
import corp.jasane.provider.modules.ViewModelFactory
import corp.jasane.provider.modules.biodata.ui.BiodataActivity
import corp.jasane.provider.modules.home.ui.HomeActivity
import corp.jasane.provider.modules.verificationBiodata.verificationTwo.data.viewModel.VerificationTwoActivityVM
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class VerificationTwoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityVerificationTwoBinding
    private lateinit var cameraExecutor: ExecutorService
    private lateinit var cameraProviderFuture: ListenableFuture<ProcessCameraProvider>
    private lateinit var progressDialog: Dialog
    private lateinit var viewModel: VerificationTwoActivityVM
    private var imageCapture: ImageCapture? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVerificationTwoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = viewModels<VerificationTwoActivityVM> {
            ViewModelFactory.getInstance(this)
        }.value

        progressDialog = Dialog(this)
        progressDialog.setContentView(R.layout.progress_dialog)
        progressDialog.setCancelable(false)
        progressDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        cameraExecutor = Executors.newSingleThreadExecutor()
        cameraProviderFuture = ProcessCameraProvider.getInstance(this)

        binding.lottieViewCross.setAnimation(R.raw.cross)
        binding.lottieViewCheck.setAnimation(R.raw.ic_check)

//        viewModel.ktpResponse.observe(this, Observer { response ->
//            hideLoading()
//            if (response.nik.isEmpty()) {
//                val intent = Intent(this, BiodataActivity::class.java)
//                intent.putExtra(EXTRA_NAMA, response.nama)
//                intent.putExtra(EXTRA_NIK, response.nik)
//                startActivity(intent)
//                finish()
//            } else {
//                // Tanggapan gagal, tampilkan pesan kesalahan atau lakukan tindakan lainnya
//                showErrorDialog(response.message)
//            }
//        })

        checkCameraPermission()
        setupView()
    }

    private fun showErrorDialog(errorMessage: String) {
        AlertDialog.Builder(this)
            .setTitle("Error")
            .setMessage(errorMessage)
            .setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    private fun setupView() {
        supportActionBar?.apply {
            title = getString(R.string.title_scan_ktp)
            setDisplayHomeAsUpEnabled(true)
        }
    }

    private fun startCamera() {
        cameraProviderFuture.addListener({
            val cameraProvider = cameraProviderFuture.get()

            val preview = Preview.Builder().build()
            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            try {
                // Bind the preview use case
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(
                    this, cameraSelector, preview
                )

                val previewView: PreviewView = binding.cameraPreview
                preview.setSurfaceProvider(previewView.surfaceProvider)

                // Create and bind the ImageCapture use case
                imageCapture = ImageCapture.Builder()
                    .setTargetResolution(Size(previewView.width, previewView.height))
                    .build()
                cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageCapture)

                // Set the click listener for capturing an image
                binding.buttonImage.setOnClickListener {
                    showLoading()
                    val imageFile = File(externalMediaDirs.first(), "${System.currentTimeMillis()}.jpg")

                    val outputOptions = ImageCapture.OutputFileOptions.Builder(imageFile).build()

//                    val previewWidth = previewView.width
//                    val previewHeight = previewView.height
//
//                    // Create the pixels array with dimensions matching the PreviewView
//                    val pixels = IntArray(previewWidth * previewHeight)
//                    imageCapture?.targetResolution = previewSize

                    // Ensure that the ImageCapture use case is not null before taking a picture
                    imageCapture?.takePicture(
                        outputOptions,
                        ContextCompat.getMainExecutor(this),
                        object : ImageCapture.OnImageSavedCallback {
                            override fun onError(error: ImageCaptureException) {
                                Log.d("errorTakePicture", "$error")
                                hideLoading()
                            }

                            override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                                // Image captured successfully, now call the ViewModel function
                                lifecycleScope.launch {
                                    try {
                                        // Use your custom functions to reduce image size
                                        val reducedImageFile = imageFile.reduceFileImageKtp()

                                        viewModel.captureAndUploadImage(
                                            reducedImageFile,
                                            onSuccess = { successResponse ->
                                                Log.d(
                                                    "onImageSaved - success",
                                                    successResponse.toString()
                                                )
                                                if (successResponse?.nama?.isNotEmpty() == true && successResponse?.nik?.isNotEmpty() == true) {

                                                    val intent = Intent(this@VerificationTwoActivity, BiodataActivity::class.java).apply {
                                                        putExtra(BiodataActivity.EXTRA_NAMA, successResponse.nama)
                                                        putExtra(BiodataActivity.EXTRA_NIK, successResponse.nik)
                                                    }
                                                    startActivity(intent)
                                                } else {
                                                    // Menanggapi jika data nama atau nik kosong
                                                    hideLoading()
//                                                    showErrorDialog("Nama atau NIK kosong")
                                                }


                                                // Handle success response as needed
                                            },
                                            onError = { errorMessage ->
                                                hideLoading()
                                                AlertDialog.Builder(this@VerificationTwoActivity)
//                                                    .setTitle("Error")
//                                                    .setMessage("Coba Ulangi Lagi!")
//                                                    .setPositiveButton("OK") { dialog, _ ->
//                                                        dialog.dismiss()
//                                                    }
//                                                    .show()
                                                Log.d("errordata", "$errorMessage")
                                            }
                                        )
                                    } catch (e: Exception) {
                                        // Handle exceptions here
                                        hideLoading()
                                        Toast.makeText(
                                            this@VerificationTwoActivity,
                                            "Error reducing image size: ${e.message}",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                }
                            }
                        }
                    )
                }

            } catch (exc: Exception) {
                Log.e(TAG, "Error starting camera", exc)
            }
        }, ContextCompat.getMainExecutor(this))
    }

    override fun onDestroy() {
        super.onDestroy()
        cameraProviderFuture.addListener({
            val cameraProvider = cameraProviderFuture.get()
            cameraProvider?.unbindAll()
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

    private fun showLoading() {
        progressDialog.show()
    }

    private fun hideLoading() {
        progressDialog.dismiss()
    }

    companion object {
        private const val REQUEST_CAMERA_PERMISSION_CODE = 1001
        const val EXTRA_NAMA = "extra_nama"
        const val EXTRA_NIK = "extra_nik"
        private const val TAG = "VerificationTwoActivity"
    }
}
