package corp.jasane.provider.modules.splashscreen.`data`.model

import corp.jasane.provider.R
import corp.jasane.provider.appcomponents.di.MyApp
import kotlin.String

data class SplashScreenModel(
  var txtTitleOnboarding: String? = MyApp.getInstance().resources.getString(R.string.lbl_jasane)

)
