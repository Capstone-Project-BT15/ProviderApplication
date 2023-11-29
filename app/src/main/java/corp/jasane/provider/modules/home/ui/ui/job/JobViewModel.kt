package corp.jasane.provider.modules.home.ui.ui.job

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class JobViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Job Fragment"
    }
    val text: LiveData<String> = _text
}