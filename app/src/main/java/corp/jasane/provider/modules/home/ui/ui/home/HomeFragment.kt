package corp.jasane.provider.modules.home.ui.ui.home

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import corp.jasane.provider.R
import corp.jasane.provider.databinding.FragmentHomeBinding
import corp.jasane.provider.modules.ViewModelFactory
import corp.jasane.provider.modules.login.ui.LoginActivity
import corp.jasane.provider.modules.verificationBiodata.verificationFirst.ui.VerificationFirstActivity
import corp.jasane.provider.modules.verificationBiodata.verificationTwo.VerificationTwoActivity

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<HomeFragmentViewModel> {
        ViewModelFactory.getInstance(requireContext())
    }

    private lateinit var progressDialog: Dialog

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        progressDialog = Dialog(requireContext())
        progressDialog.setContentView(R.layout.progress_dialog)
        progressDialog.setCancelable(false)
        progressDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        viewModel.getSession().observe(viewLifecycleOwner) { user ->
            if (!user.isLogin) {
                startActivity(Intent(requireContext(), LoginActivity::class.java))
                requireActivity().
                finish()
            } else {
                hideLoading()
            }
        }
        return root
    }

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        val spanCount = 2
//        val layoutManager = StaggeredGridLayoutManager(spanCount, StaggeredGridLayoutManager.VERTICAL)
//        val recyclerView: RecyclerView = binding.recyclerListWorker
//        recyclerView.layoutManager = layoutManager
//    }

    private fun showLoading() {
        progressDialog.show()
    }

    private fun hideLoading() {
        progressDialog.dismiss()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}