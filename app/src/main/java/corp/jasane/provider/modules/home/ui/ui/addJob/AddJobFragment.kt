package corp.jasane.provider.modules.home.ui.ui.addJob

import android.Manifest
import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.location.Geocoder
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.maps.model.LatLng
import com.google.android.material.bottomsheet.BottomSheetDialog
import corp.jasane.provider.R
import corp.jasane.provider.appcomponents.utility.DatePickerFragment
import corp.jasane.provider.appcomponents.utility.getImageUri
import corp.jasane.provider.appcomponents.utility.getRotatedBitmap
import corp.jasane.provider.appcomponents.utility.reduceFileImage
import corp.jasane.provider.appcomponents.utility.uriToFile
import corp.jasane.provider.data.local.ItemJob
import corp.jasane.provider.data.local.createJobList
import corp.jasane.provider.data.response.Category
import corp.jasane.provider.databinding.FragmentAddJobBinding
import corp.jasane.provider.modules.ViewModelFactory
import corp.jasane.provider.modules.home.ui.HomeActivity
import corp.jasane.provider.modules.home.ui.ui.maps.MapsFragment
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class AddJobFragment : Fragment(), DatePickerFragment.DialogDateListener, MapsFragment.OnLocationSelectedListener {

    private var _binding: FragmentAddJobBinding? = null
    private lateinit var selectedItem:TextView
    private lateinit var dialog: BottomSheetDialog
    private lateinit var addJobAdapter: AddJobAdapter
    private lateinit var recyclerView: RecyclerView
    private var currentImageUri: Uri? = null
    private var lat: String = ""
    private var lon: String = ""
    private var categoryId: Int =  0
    private val viewModel by viewModels<AddJobViewModel> {
        ViewModelFactory.getInstance(requireContext())
    }
    private lateinit var progressDialog: Dialog

    private val binding get() = _binding!!

    private val launcherGallery = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            currentImageUri = uri
            showImage()
        }
    }

    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { isSuccess ->
        if (isSuccess) {
            showImage()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentAddJobBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val bottomSheetView = layoutInflater.inflate(R.layout.list_bottom_sheet, null)
        progressDialog = Dialog(requireContext())
        progressDialog.setContentView(R.layout.progress_dialog)
        progressDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        progressDialog.setCancelable(false)

        fun allPermissionsGranted() =
            ContextCompat.checkSelfPermission(
                requireContext(),
                REQUIRED_PERMISSION
            ) == PackageManager.PERMISSION_GRANTED

        val requestPermissionLauncher =
            registerForActivityResult(
                ActivityResultContracts.RequestPermission()
            ) { isGranted: Boolean ->
                if (isGranted) {
                    Toast.makeText(requireContext(), "Permission request granted", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(requireContext(), "Permission request denied", Toast.LENGTH_LONG).show()
                }
            }

        if (!allPermissionsGranted()) {
            requestPermissionLauncher.launch(REQUIRED_PERMISSION)
        }

        val categoryTextView: TextView = binding.category
        val typeJob: TextView = binding.job
        val imageJob: ImageView = binding.tvItemPhoto

        categoryTextView.setOnClickListener {
            showLoading()
            viewModel.detailCategory.observe(viewLifecycleOwner) { categoryResponse ->
                categoryResponse?.let {
                    showBottomSheet(it.data)
                }
                hideLoading()
            }
        }

        typeJob.setOnClickListener {
            showLoading()
            val jobList = createJobList(requireContext())
            categoryJobBottomSheet(jobList)
            hideLoading()
        }

        binding.date.setOnClickListener {
            showDatePicker(it)
        }

        binding.pinPointText.setOnClickListener {
            showLoading()
            onPinPointTextClicked(it)
            hideLoading()
        }

        imageJob.setOnClickListener {

            val options = arrayOf("Gallery", "Camera")

            val builder = AlertDialog.Builder(requireContext())
            builder.setTitle("Choose an option")
            builder.setItems(options) { dialog: DialogInterface, which: Int ->
                when (which) {
                    0 -> startGallery()
                    1 -> startCamera()
                }
                dialog.dismiss()
            }
            builder.show()
        }

        binding.upload.setOnClickListener {
            uploadImage()
        }

        viewModel.insertWorkResponse.observe(viewLifecycleOwner) { insertWorkResponse ->
            insertWorkResponse?.let {
                // Check the 'success' status directly
                if (it.meta.status == "success") {
                    hideLoading()
                    AlertDialog.Builder(requireContext())
                        .setTitle("Success")
                        .setMessage(it.meta.message)
                        .setPositiveButton("OK") { dialog, _ ->
                            dialog.dismiss()
                            val intent = Intent(requireContext(), HomeActivity::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                            startActivity(intent)
                        }
                        .show()
                } else {
                    // Handle error case
                    showToast("Error uploading work: ${it.meta.message}")
                }
            }
        }
        return root
    }

    private fun showBottomSheet(categoryList: List<Category>) {
        val dialogView = layoutInflater.inflate(R.layout.bottom_sheet, null)
        dialog = BottomSheetDialog(requireContext(), R.style.BottomSheetDialogTheme)
        dialog.setContentView(dialogView)
        recyclerView = dialogView.findViewById(R.id.rv_item)
        addJobAdapter = AddJobAdapter(categoryList.map { it.title }) { selectedItem ->
            val selectedJob = categoryList.toList().find { it.title == selectedItem }
            binding.category.text = selectedItem
            categoryId = selectedJob?.id ?: 0
            Log.d("SelectedJobDebug", "Selected Name: $selectedItem, Selected ID: ${selectedJob?.id}")
            dialog.dismiss()
        }

        recyclerView.adapter = addJobAdapter
        dialog.show()
    }

    private fun categoryJobBottomSheet(categoryList: List<ItemJob>) {
        val dialogView = layoutInflater.inflate(R.layout.bottom_sheet, null)
        dialog = BottomSheetDialog(requireContext(), R.style.BottomSheetDialogTheme)
        dialog.setContentView(dialogView)
        recyclerView = dialogView.findViewById(R.id.rv_item)
        addJobAdapter = AddJobAdapter(categoryList.map { it.nameJob }) { selectedItem ->
            val selectedJob = categoryList.toList().find { it.nameJob == selectedItem }
            Log.d("SelectedJobDebug", "Selected Name: $selectedItem, Selected ID: ${selectedJob?.id}")

            binding.job.text = selectedItem
            dialog.dismiss()
        }

        recyclerView.adapter = addJobAdapter
        dialog.show()
    }

    private fun showDatePicker(view: View) {
        val datePickerFragment = DatePickerFragment()
        datePickerFragment.show(childFragmentManager, "datePicker")
    }

    override fun onDialogDateSet(tag: String?, year: Int, month: Int, dayOfMonth: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(year, month, dayOfMonth)
        val dateFormat = SimpleDateFormat("yyyy/MM/dd", Locale.getDefault())
        binding.date.text = dateFormat.format(calendar.time)
    }

    fun onPinPointTextClicked(view: View) {
        // Show the bottom sheet with the MapsFragment
        val bottomSheet = MapsFragment()
        bottomSheet.setLocationSelectedListener(this)
        bottomSheet.show(parentFragmentManager, bottomSheet.tag)
    }

    override fun onLocationSelected(latLng: LatLng) {
        latLng.let {
            val geocoder = Geocoder(requireContext(), Locale.getDefault())
            try {
                val addresses = geocoder.getFromLocation(it.latitude, it.longitude, 1)
                val city = addresses?.firstOrNull()?.getAddressLine(0) ?: "Unknown"
                binding.pinPointText.text = city
                lat = it.latitude.toString()
                lon = it.longitude.toString()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        Log.d("cekLatLong", "$latLng")
    }

    override fun onLocation(latLng: LatLng) {
        latLng.let {
            val geocoder = Geocoder(requireContext(), Locale.getDefault())
            try {
                val addresses = geocoder.getFromLocation(it.latitude, it.longitude, 1)
                val city = addresses?.firstOrNull()?.getAddressLine(0) ?: "Unknown"
                binding.pinPointText.text = city
                lat = it.latitude.toString()
                lon = it.longitude.toString()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        Log.d("updatePinPointText", "$latLng")
    }
    private fun startGallery() {
        launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private fun startCamera() {
        currentImageUri = getImageUri(requireContext())
        launcherIntentCamera.launch(currentImageUri)
    }

    private fun showImage() {
        val imageView: ImageView = binding.tvItemPhoto
        currentImageUri?.let { uri ->

            val file = uriToFile(uri, requireContext())

            val rotatedBitmap = BitmapFactory.decodeFile(file.path).getRotatedBitmap(file)

            imageView.setImageBitmap(rotatedBitmap)
        }
    }

    private fun uploadImage() {
        showLoading()
        currentImageUri?.let { uri ->
            lifecycleScope.launch {
                try {
                    val imageFile = uriToFile(uri, requireContext()).reduceFileImage()
                    val title = binding.titleEditText.text.toString()
                    val categoryId = this@AddJobFragment.categoryId
                    val telephone = "082174532772"
                    val minBudgetString = binding.minEditText.text.toString()
                    val maxBudgetString = binding.maxEditText.text.toString()
                    val minBudget = minBudgetString.replace(".", "").replace(",", "")
                    val maxBudget = maxBudgetString.replace(".", "").replace(",", "")
                    val typeOfWork = binding.job.text.toString()
                    val startDate = binding.date.text.toString()
                    val description = binding.editDesciption.text.toString()
                    val lat = this@AddJobFragment.lat
                    val lon = this@AddJobFragment.lon

                    viewModel.uploadWork(
                        imageFile,
                        title,
                        categoryId,
                        telephone,
                        minBudget,
                        maxBudget,
                        typeOfWork,
                        startDate,
                        description,
                        lat,
                        lon,
                    )
                } catch (e: Exception) {
                    // Handle exceptions here
                    hideLoading()
                    Toast.makeText(requireContext(), "Error uploading image: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun showLoading() {
        progressDialog.show()
    }

    private fun hideLoading() {
        progressDialog.dismiss()
    }
    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val REQUIRED_PERMISSION = Manifest.permission.CAMERA
    }
}