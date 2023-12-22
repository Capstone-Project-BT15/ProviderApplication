package corp.jasane.provider.modules

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import corp.jasane.provider.appcomponents.di.Injection
import corp.jasane.provider.data.UserRepository
import corp.jasane.provider.data.retrofit.ApiService
import corp.jasane.provider.data.retrofit.ApiServiceOCR
import corp.jasane.provider.modules.biodata.data.BiodataActivityVM
import corp.jasane.provider.modules.home.ui.ui.addJob.AddJobViewModel
import corp.jasane.provider.modules.home.ui.ui.home.HomeFragmentViewModel
import corp.jasane.provider.modules.home.ui.ui.offers.data.viewModel.OffersStatusAcceptedFragmentVM
import corp.jasane.provider.modules.home.ui.ui.offers.data.viewModel.OffersStatusFinishFragmentVM
import corp.jasane.provider.modules.home.ui.ui.offers.data.viewModel.OffersStatusOfferFragmentVM
import corp.jasane.provider.modules.home.ui.ui.offers.ui.OffersStatusAcceptedFragment
import corp.jasane.provider.modules.home.ui.ui.offers.ui.OffersStatusFinishFragment
import corp.jasane.provider.modules.home.ui.ui.offers.ui.OffersStatusOfferFragment
import corp.jasane.provider.modules.home.ui.ui.profile.ProfileViewModel
import corp.jasane.provider.modules.login.data.viewModel.LoginActivityViewModel
import corp.jasane.provider.modules.payment.data.PaymentActivityViewModel
import corp.jasane.provider.modules.register.data.viewModel.RegisterActivityViewModel
import corp.jasane.provider.modules.verificationBiodata.verificationTwo.data.viewModel.VerificationTwoActivityVM

class ViewModelFactory(
    private val userRepository: UserRepository,
    private val apiService: ApiService,
    private val apiServiceOCR: ApiServiceOCR
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(LoginActivityViewModel::class.java) -> {
                LoginActivityViewModel(userRepository, apiService) as T
            }
            modelClass.isAssignableFrom(AddJobViewModel::class.java) -> {
                AddJobViewModel(apiService, userRepository) as T
            }
            modelClass.isAssignableFrom(HomeFragmentViewModel::class.java) -> {
                HomeFragmentViewModel(userRepository, apiService) as T
            }
            modelClass.isAssignableFrom(RegisterActivityViewModel::class.java) -> {
                RegisterActivityViewModel(userRepository, apiService) as T
            }
            modelClass.isAssignableFrom(ProfileViewModel::class.java) -> {
                ProfileViewModel(userRepository, apiService) as T
            }
            modelClass.isAssignableFrom(OffersStatusOfferFragmentVM::class.java) -> {
                OffersStatusOfferFragmentVM(userRepository, apiService) as T
            }
            modelClass.isAssignableFrom(OffersStatusAcceptedFragmentVM::class.java) -> {
                OffersStatusAcceptedFragmentVM(userRepository, apiService) as T
            }
            modelClass.isAssignableFrom(OffersStatusFinishFragmentVM::class.java) -> {
                OffersStatusFinishFragmentVM(userRepository, apiService) as T
            }
            modelClass.isAssignableFrom(PaymentActivityViewModel::class.java) -> {
                PaymentActivityViewModel(apiService, userRepository) as T
            }
            modelClass.isAssignableFrom(VerificationTwoActivityVM::class.java) -> {
                VerificationTwoActivityVM(apiServiceOCR) as T
            }
            modelClass.isAssignableFrom(BiodataActivityVM::class.java) -> {
                BiodataActivityVM(apiService, userRepository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null
        @JvmStatic
        fun getInstance(context: Context): ViewModelFactory {
            if (INSTANCE == null) {
                synchronized(ViewModelFactory::class.java) {
                    INSTANCE = ViewModelFactory(
                        Injection.provideRepository(context),
                        Injection.provideApiService(),
                        Injection.provideApiServiceOCR(),
                    )
                }
            }
            return INSTANCE as ViewModelFactory
        }
    }
}