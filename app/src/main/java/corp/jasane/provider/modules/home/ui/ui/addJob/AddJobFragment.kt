package corp.jasane.provider.modules.home.ui.ui.addJob

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import corp.jasane.provider.databinding.FragmentAddJobBinding

class AddJobFragment : Fragment() {

    private var _binding: FragmentAddJobBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val addJobViewModel =
            ViewModelProvider(this).get(AddJobViewModel::class.java)

        _binding = FragmentAddJobBinding.inflate(inflater, container, false)
        val root: View = binding.root

//        val textView: TextView = binding.textHome
//        addJobViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}