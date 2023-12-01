package corp.jasane.provider.modules.home.ui.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text

    private val _jobItemList = MutableLiveData<List<JobItem>>()
    val jobItemList: LiveData<List<JobItem>> get() = _jobItemList

    init {
        // Populate the job items (replace this with your actual data source)
        val items = listOf(
            JobItem("Job 1", "1005 kilometer", "Details 1", "Budget 1", "Rp100,000", "Rp120,000"),
            JobItem("Job 2", "10 km", "Details 2", "Budget 2", "Rp150,000", "Rp180,000"),
            JobItem("Job 3", "10 km", "Details 3", "Budget 2", "Rp1500,000", "Rp180,000"),
            JobItem("Job 42143", "10 km", "Details 4", "Budget 2", "Rp150,000", "Rp180,000"),
            JobItem("Job 5", "5 km", "Details 5", "Budget 1", "Rp100,000", "Rp120,000"),
            JobItem("Job 6", "10 km", "Details 6", "Budget 2", "Rp150,000", "Rp180,000"),
            JobItem("Job 7", "10 km", "Details 7", "Budget 2", "Rp150,000", "Rp180,000"),
            JobItem("Job 8", "10 km", "Details 8", "Budget 2", "Rp1,250,000", "Rp1,800,000"),
            JobItem("Job 9", "5 km", "Details 9", "Budget 1", "Rp100,000", "Rp120,000"),
            JobItem("Job 10", "10 km", "Details 10", "Budget 2", "Rp150,000", "Rp1,800,000"),
            JobItem("Job 11", "10 km", "Details 11", "Budget 2", "Rp150,000", "Rp180,000"),
            JobItem("Job 12", "10 km", "Details 12", "Budget 2", "Rp150,000", "Rp180,000"),
            // Add more items as needed
        )
        _jobItemList.value = items
    }
}