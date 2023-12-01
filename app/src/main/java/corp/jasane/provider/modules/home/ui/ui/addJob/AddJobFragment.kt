package corp.jasane.provider.modules.home.ui.ui.addJob

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import corp.jasane.provider.R
import corp.jasane.provider.databinding.FragmentAddJobBinding

class AddJobFragment : Fragment() {

    private var _binding: FragmentAddJobBinding? = null
    private lateinit var selectedItem:TextView
    private lateinit var dialog: BottomSheetDialog
    private lateinit var addJobAdapter: AddJobAdapter
    private lateinit var recyclerView: RecyclerView
    private val list = ArrayList<String>()

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
        val bottomSheetView = layoutInflater.inflate(R.layout.list_bottom_sheet, null)
        for (i in 1..20) {
            list.add("item $i")
        }

        val categoryTextView: TextView = binding.category

        categoryTextView.setOnClickListener {
            showBottomSheet()
        }

//        val textView: TextView = binding.textHome
//        addJobViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }
        return root
    }

    private fun showBottomSheet() {
        val dialogView = layoutInflater.inflate(R.layout.bottom_sheet, null)
        dialog = BottomSheetDialog(requireContext(), R.style.BottomSheetDialogTheme)
        dialog.setContentView(dialogView)
        recyclerView = dialogView.findViewById(R.id.rv_item)
        addJobAdapter = AddJobAdapter(list) { selectedItem ->
            binding.category.text = selectedItem
            dialog.dismiss()
        }

        recyclerView.adapter = addJobAdapter
        dialog.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}