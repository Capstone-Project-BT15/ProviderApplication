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
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import corp.jasane.provider.R
import corp.jasane.provider.data.response.DataOfferFinished
import corp.jasane.provider.data.response.UserData
import corp.jasane.provider.data.response.WorkItem
import corp.jasane.provider.databinding.FragmentOffersStatusAcceptedBinding
import corp.jasane.provider.databinding.FragmentOffersStatusFinishBinding
import corp.jasane.provider.databinding.FragmentOffersStatusOfferBinding
import corp.jasane.provider.modules.ViewModelFactory
import corp.jasane.provider.modules.home.ui.HomeActivity
import corp.jasane.provider.modules.home.ui.ui.offers.data.viewModel.OffersStatusAcceptedFragmentVM
import corp.jasane.provider.modules.home.ui.ui.offers.data.viewModelAdapter.OffersStatusAcceptedAdapter
import corp.jasane.provider.modules.home.ui.ui.offers.data.viewModelAdapter.OffersStatusFinishAdapter
import corp.jasane.provider.modules.payment.ui.PaymentActivity
import kotlinx.coroutines.launch

class OffersStatusAcceptedFragment : Fragment() {

    private lateinit var binding: FragmentOffersStatusAcceptedBinding
    private val viewModel by viewModels<OffersStatusAcceptedFragmentVM> {
        ViewModelFactory.getInstance(requireContext())
    }
    private lateinit var progressDialog: Dialog
    private lateinit var rootView: View

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
        binding = FragmentOffersStatusAcceptedBinding.inflate(inflater, container, false)
        rootView = binding.root
        val status = arguments?.getString(ARG_STATUS)

        val recyclerView: RecyclerView = binding.recyclerListWorker
        val adapter = OffersStatusAcceptedAdapter(requireContext())

        recyclerView.adapter = adapter

        viewModel.workDetails.observe(viewLifecycleOwner) { workDetails ->
            adapter.setList(workDetails)
            hideLoading()
        }

        viewModel.offerFinished.observe(viewLifecycleOwner) {DataOfferFinished ->
            if (DataOfferFinished?.offerId != null){
                val intent = Intent(requireContext(), HomeActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
                requireActivity().finish()
                Toast.makeText(requireContext(), "Pekerjaan Telah Selesai", Toast.LENGTH_SHORT).show()
                hideLoading()
            }

        }

        adapter.setOnItemClickCallback(object : OffersStatusAcceptedAdapter.OnItemClickCallBack {
            override fun onItemClicked(data: WorkItem) {
                // Handle click on WorkItem
            }
            override fun onItemClickedData(data: UserData) {
                showLoading()
                val button = rootView.findViewById<Button>(R.id.button_item_accepted)
                button.setOnClickListener {
                    try {
                        lifecycleScope.launch {
                            val id = data.id

                            viewModel.offerFinished(id)
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
        private const val ARG_STATUS = "Accept"

        fun newInstance(status: String): OffersStatusAcceptedFragment {
            val fragment = OffersStatusAcceptedFragment()
            val args = Bundle()
            args.putString(ARG_STATUS, status)
            fragment.arguments = args
            return fragment
        }
    }
}