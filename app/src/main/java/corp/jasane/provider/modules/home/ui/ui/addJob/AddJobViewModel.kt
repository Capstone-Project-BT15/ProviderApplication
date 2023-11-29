package corp.jasane.provider.modules.home.ui.ui.addJob

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AddJobViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is AddJob Fragment"
    }
    val text: LiveData<String> = _text
}