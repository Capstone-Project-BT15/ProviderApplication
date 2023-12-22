package corp.jasane.provider.modules.payment.ui

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import corp.jasane.provider.R
import corp.jasane.provider.data.response.DataInsertPayment
import corp.jasane.provider.databinding.ActivityPaymentBinding
import corp.jasane.provider.modules.ViewModelFactory
import corp.jasane.provider.modules.home.ui.HomeActivity
import corp.jasane.provider.modules.home.ui.ui.home.HomeFragment
import corp.jasane.provider.modules.home.ui.ui.offers.ui.OffersFragment
import corp.jasane.provider.modules.home.ui.ui.offers.ui.OffersStatusAcceptedFragment
import corp.jasane.provider.modules.payment.data.PaymentActivityViewModel

class PaymentActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPaymentBinding
    private lateinit var viewModel: PaymentActivityViewModel
    private lateinit var progressDialog: Dialog
    private var paymentMethod = "Cash"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaymentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this, ViewModelFactory.getInstance(this))[PaymentActivityViewModel::class.java]

        progressDialog = Dialog(this)
        progressDialog.setContentView(R.layout.progress_dialog)
        progressDialog.setCancelable(false)
        progressDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        showLoading()

//        val id = intent.getIntExtra(EXTRA_ID, 0)
        val id = intent.getIntExtra(EXTRA_ID, 0)
        val distance = intent.getStringExtra(EXTRA_DISTANCE)
        val userPhoto = intent.getStringExtra(EXTRA_USER_PHOTO)
        Log.d("id", "$id")
        viewModel.init(id)

        viewModel.paymentDetail.observe(this) { paymentResponse ->
            if (paymentResponse?.data != null) {
                binding.apply {
                    postingan.text = distance.toString()
                    textName.text = paymentResponse.data.address.fullname
                    priceOffer.text = paymentResponse.data.tariff
                    phoneNumber.text = paymentResponse.data.address.telephone
                    detailAddress.text = paymentResponse.data.address.address
                    pricePayment.text = paymentResponse.data.tariff
                    priceAdmin.text = paymentResponse.data.adminFees.toString()
                    totalPayment.text = paymentResponse.data.total.toString()

                    Glide.with(this@PaymentActivity)
                        .load(userPhoto)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .centerCrop()
                        .into(tvItemPhoto)
                }
                hideLoading()
            } else {
                // Handle null response
            }
        }
        setupAction()
        setupView()
    }

    private fun setupView(){
        actionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setupAction(){
        showLoading()
        binding.buttonSave.setOnClickListener {
            val id = intent.getIntExtra(EXTRA_ID, 0)
            val tariff = viewModel.paymentDetail.value?.data?.tariff?.toIntOrNull() ?: 0
            val adminFees = viewModel.paymentDetail.value?.data?.adminFees ?: 0
            val total = viewModel.paymentDetail.value?.data?.total ?: 0

            val paymentMethod = paymentMethod

            viewModel.inserPayment(id, paymentMethod, tariff, adminFees, total)
        }
        viewModel.insertPayment.observe(this){dataInsertPayment ->

            if (dataInsertPayment?.id != null) {
                val intent = Intent(this, HomeActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
                finish()
                Toast.makeText(this, "Berhasil Memilih Pekerja", Toast.LENGTH_SHORT).show()
                hideLoading()
            }

        }
    }

    private fun showLoading() {
        progressDialog.show()
    }

    private fun hideLoading() {
        progressDialog.dismiss()
    }

    companion object{
        const val EXTRA_ID = "extra_id"
        const val EXTRA_USER_PHOTO = "extra_user_photo"
        const val EXTRA_DISTANCE = "extra_distance"
    }
}