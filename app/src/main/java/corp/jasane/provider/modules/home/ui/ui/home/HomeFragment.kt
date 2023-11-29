package corp.jasane.provider.modules.home.ui.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import corp.jasane.provider.databinding.FragmentHomeBinding
import corp.jasane.provider.databinding.ItemWorkerBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModels()

    private val adapter = JobAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val spanCount = 2
        val layoutManager = StaggeredGridLayoutManager(spanCount, StaggeredGridLayoutManager.VERTICAL)
        val recyclerView: RecyclerView = binding.recyclerListWorker
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter

        viewModel.jobItemList.observe(viewLifecycleOwner) { jobItems ->
            adapter.submitList(jobItems)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

data class JobItem(
    val jobName: String,
    val jobDistance: String,
    val jobDetail: String,
    val budget: String,
    val lowPrice: String,
    val highPrice: String
)

class JobAdapter : ListAdapter<JobItem, JobViewHolder>(JobItemDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JobViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemWorkerBinding.inflate(inflater, parent, false)
        return JobViewHolder(binding)
    }

    override fun onBindViewHolder(holder: JobViewHolder, position: Int) {
        val jobItem = getItem(position)
        holder.bind(jobItem)
    }
}

class JobViewHolder(private val binding: ItemWorkerBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(jobItem: JobItem) {

        binding.jobName.text = jobItem.jobName
        binding.jobDistance.text = jobItem.jobDistance
        binding.jobDetail.text = jobItem.jobDetail
        binding.budget.text = jobItem.budget
        binding.lowPrice.text = jobItem.lowPrice
        binding.highPrice.text = jobItem.highPrice
    }
}

class JobItemDiffCallback : DiffUtil.ItemCallback<JobItem>() {
    override fun areItemsTheSame(oldItem: JobItem, newItem: JobItem): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: JobItem, newItem: JobItem): Boolean {
        return oldItem == newItem
    }
}
