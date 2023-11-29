package corp.jasane.provider.modules.home.ui.ui.job

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import corp.jasane.provider.R
import corp.jasane.provider.databinding.FragmentHomeBinding
import corp.jasane.provider.databinding.FragmentJobBinding
import corp.jasane.provider.modules.home.ui.ui.home.HomeViewModel

class JobFragment : Fragment() {
    private var _binding: FragmentJobBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val jobViewModel =
            ViewModelProvider(this).get(JobViewModel::class.java)

        _binding = FragmentJobBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textHome
        jobViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}