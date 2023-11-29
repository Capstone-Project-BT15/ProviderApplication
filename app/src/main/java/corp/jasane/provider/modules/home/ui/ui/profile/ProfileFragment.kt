package corp.jasane.provider.modules.home.ui.ui.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import corp.jasane.provider.R
import corp.jasane.provider.databinding.FragmentAddJobBinding
import corp.jasane.provider.databinding.FragmentOffersBinding
import corp.jasane.provider.databinding.FragmentProfileBinding
import corp.jasane.provider.modules.home.ui.ui.addJob.AddJobViewModel
import corp.jasane.provider.modules.home.ui.ui.offers.OffersViewModel

class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val profileViewModel =
            ViewModelProvider(this).get(ProfileViewModel::class.java)

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textHome
        profileViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}