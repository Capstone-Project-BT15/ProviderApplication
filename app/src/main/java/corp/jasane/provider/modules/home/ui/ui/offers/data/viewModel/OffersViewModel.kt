package corp.jasane.provider.modules.home.ui.ui.offers.data.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class OffersViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Offers Fragment"
    }
    val text: LiveData<String> = _text
}