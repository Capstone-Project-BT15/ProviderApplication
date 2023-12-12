package corp.jasane.provider.modules.home.ui.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import corp.jasane.provider.data.UserRepository
import corp.jasane.provider.data.pref.UserModel

class HomeFragmentViewModel(private val userRepository: UserRepository) : ViewModel() {

    fun getSession(): LiveData<UserModel> {
        return userRepository.getSession().asLiveData()
    }
}