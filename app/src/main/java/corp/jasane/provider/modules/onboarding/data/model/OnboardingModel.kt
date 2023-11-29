package corp.jasane.provider.modules.onboarding.`data`.model

import corp.jasane.provider.R
import corp.jasane.provider.appcomponents.di.MyApp
import kotlin.String

data class OnboardingModel(

  var txtTitleOnboarding: String? =
      MyApp.getInstance().resources.getString(R.string.msg_selamat_datang)
  ,

  var txtDescription: String? = MyApp.getInstance().resources.getString(R.string.msg_tempat_di_mana)

)
