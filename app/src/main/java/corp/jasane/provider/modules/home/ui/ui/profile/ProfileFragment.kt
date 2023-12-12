package corp.jasane.provider.modules.home.ui.ui.profile

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import com.airbnb.lottie.LottieAnimationView
import corp.jasane.provider.R
import corp.jasane.provider.databinding.FragmentProfileBinding
import corp.jasane.provider.modules.ViewModelFactory
import corp.jasane.provider.modules.login.ui.LoginActivity

class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null

    private val binding get() = _binding!!
    private val viewModel by viewModels<ProfileViewModel> {
        ViewModelFactory.getInstance(requireContext())
    }
    private lateinit var dialogView: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
//        val profileViewModel =
//            ViewModelProvider(this).get(ProfileViewModel::class.java)

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root
        dialogView = inflater.inflate(R.layout.on_progress, null) // Initialize dialogView

        // The rest of your code

        val lottieView = dialogView.findViewById<LottieAnimationView>(R.id.lottieView)
        lottieView.setAnimation(R.raw.business_salesman)

        val alertDialog = AlertDialog.Builder(requireContext(), R.style.MyAlertDialog)
            .setView(dialogView)
            .setCancelable(false)
            .create()

        val buttonOnProgress = dialogView.findViewById<Button>(R.id.button_on_progress)

        buttonOnProgress.setOnClickListener {
            alertDialog.dismiss()
        }

        binding.cardViewAbout.setOnClickListener {
            alertDialog.show()
        }
        binding.cardViewRating.setOnClickListener {
            alertDialog.show()
        }
        binding.cardViewSetAddress.setOnClickListener {
            alertDialog.show()
        }
        binding.cardViewShare.setOnClickListener {
            alertDialog.show()
        }
        binding.cardViewChangePassword.setOnClickListener {
            alertDialog.show()
        }
        binding.cardViewWithdraw.setOnClickListener {
            alertDialog.show()
        }
        setupAction()
        return root
    }

    private fun setupAction() {
        binding.cardViewLogout.setOnClickListener {
            viewModel.logout()
        }
        viewModel.logoutSuccess.observe(viewLifecycleOwner) { success ->
            if (success) {
                val intent = Intent(requireContext(), LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                requireActivity().finish()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}