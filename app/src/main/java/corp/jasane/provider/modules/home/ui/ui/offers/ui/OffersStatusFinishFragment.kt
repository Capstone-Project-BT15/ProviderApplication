package corp.jasane.provider.modules.home.ui.ui.offers.ui

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import corp.jasane.provider.R
import corp.jasane.provider.data.response.UserData
import corp.jasane.provider.data.response.WorkItem
import corp.jasane.provider.databinding.FragmentOffersStatusFinishBinding
import corp.jasane.provider.databinding.FragmentOffersStatusOfferBinding
import corp.jasane.provider.databinding.ListItemOffersFinishBinding
import corp.jasane.provider.databinding.ListItemOffersWorkerBinding
import corp.jasane.provider.modules.ViewModelFactory
import corp.jasane.provider.modules.home.ui.ui.offers.data.viewModel.OffersStatusFinishFragmentVM
import corp.jasane.provider.modules.home.ui.ui.offers.data.viewModel.OffersStatusOfferFragmentVM
import corp.jasane.provider.modules.home.ui.ui.offers.data.viewModelAdapter.OffersStatusAcceptedAdapter
import corp.jasane.provider.modules.home.ui.ui.offers.data.viewModelAdapter.OffersStatusFinishAdapter
import corp.jasane.provider.modules.home.ui.ui.offers.data.viewModelAdapter.OffersStatusOfferAdapter
import corp.jasane.provider.modules.payment.ui.PaymentActivity
import kotlinx.coroutines.launch

class OffersStatusFinishFragment : Fragment() {

    private lateinit var rootView: View
    private lateinit var binding: FragmentOffersStatusFinishBinding
    private val viewModel by viewModels<OffersStatusFinishFragmentVM> {
        ViewModelFactory.getInstance(requireContext())
    }
    private lateinit var progressDialog: Dialog
    private lateinit var bindingList: ListItemOffersFinishBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        progressDialog = Dialog(requireContext())
        progressDialog.setContentView(R.layout.progress_dialog)
        progressDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        progressDialog.setCancelable(false)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOffersStatusFinishBinding.inflate(inflater, container, false)
        bindingList = ListItemOffersFinishBinding.inflate(inflater, container, false)
        rootView = binding.root
        val status = arguments?.getString(ARG_STATUS)

        val recyclerView: RecyclerView = binding.recyclerListWorker
        val recyclerViewList: RecyclerView = bindingList.recyclerListWorker
        val adapter = OffersStatusFinishAdapter(requireContext())

        recyclerView.adapter = adapter
        recyclerViewList.adapter = adapter

        viewModel.workDetails.observe(viewLifecycleOwner) { workDetails ->
            // Update UI or perform any action based on workDetails changes
            // For example, you might update the adapter's data
            adapter.setList(workDetails)
            hideLoading()
        }

        adapter.setOnItemClickCallback(object : OffersStatusFinishAdapter.OnItemClickCallBack {
            override fun onItemClicked(data: WorkItem) {
                // Handle click on WorkItem
            }
            override fun onItemClickedData(data: UserData) {
                showLoading()
                val button = rootView.findViewById<Button>(R.id.button_item_accepted)
                button.setOnClickListener {
                    try {
                        lifecycleScope.launch {
                            val paymentIntent =
                                Intent(requireContext(), PaymentActivity::class.java).apply {
                                    putExtra(PaymentActivity.EXTRA_ID, data.id)
                                    putExtra(PaymentActivity.EXTRA_USER_PHOTO, data.work_image)
                                    putExtra(PaymentActivity.EXTRA_DISTANCE, data.distance)
                                    Log.d(
                                        "setonclick",
                                        "${data.distance}, ${data.work_image}, ${data.id}"
                                    )
                                }
                            startActivity(paymentIntent)
                            hideLoading()
                        }
                    } catch (e: Exception) {
                        hideLoading()
                        Log.e("OffersStatusAcceptedFragment", "Error during button click", e)
                    }
                }
                hideLoading()
            }
        })
        return rootView
    }

    private fun showLoading() {
        progressDialog.show()
    }

    private fun hideLoading() {
        progressDialog.dismiss()
    }

    companion object {
        private const val ARG_STATUS = "Finish"

        fun newInstance(status: String): OffersStatusFinishFragment {
            val fragment = OffersStatusFinishFragment()
            val args = Bundle()
            args.putString(ARG_STATUS, status)
            fragment.arguments = args
            return fragment
        }
    }
}