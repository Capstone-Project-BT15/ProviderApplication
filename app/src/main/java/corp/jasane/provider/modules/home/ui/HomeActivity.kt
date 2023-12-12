package corp.jasane.provider.modules.home.ui

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.google.android.material.bottomnavigation.BottomNavigationView
import corp.jasane.provider.R
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.airbnb.lottie.LottieAnimationView
import corp.jasane.provider.databinding.ActivityHomeBinding
import corp.jasane.provider.modules.home.ui.ui.addJob.AddJobFragment
import corp.jasane.provider.modules.home.ui.ui.home.HomeFragment
import corp.jasane.provider.modules.home.ui.ui.job.JobFragment
import corp.jasane.provider.modules.home.ui.ui.offers.ui.OffersFragment
import corp.jasane.provider.modules.home.ui.ui.profile.ProfileFragment

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        val navView: BottomNavigationView = binding.navView
//
//        val appBarConfiguration = AppBarConfiguration(
//            setOf(
//                R.id.navigation_home, R.id.navigation_job, R.id.navigation_add_job, R.id.navigation_offers, R.id.navigation_profile
//            )
//        )
//        setupActionBarWithNavController(navController, appBarConfiguration)
//        navView.setupWithNavController(navController)
        val inflater = layoutInflater
        val dialogView = inflater.inflate(R.layout.on_progress, null)

// Temukan LottieAnimationView di dalam layout custom
        val lottieView = dialogView.findViewById<LottieAnimationView>(R.id.lottieView)
        lottieView.setAnimation(R.raw.business_salesman)

        val titleTextView = dialogView.findViewById<TextView>(R.id.titleTextView)
        val messageTextView = dialogView.findViewById<TextView>(R.id.messageTextView)

        val alertDialog = AlertDialog.Builder(this, R.style.MyAlertDialog)
            .setView(dialogView)
            .setCancelable(false)
            .create()
        val buttonOnProgress = dialogView.findViewById<Button>(R.id.button_on_progress)

        buttonOnProgress.setOnClickListener {
            alertDialog.dismiss()
        }

        val homeFragment = HomeFragment()
        val profileFragment = ProfileFragment()
        val offersFragment = OffersFragment()
        val jobFragment = JobFragment()
        val addJobFragment = AddJobFragment()

        setCurrentFragment(homeFragment)

        val bottomNavigation: BottomNavigationView = findViewById(R.id.nav_view)
        bottomNavigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_home -> setCurrentFragment(homeFragment)
                R.id.navigation_add_job -> setCurrentFragment(addJobFragment)
                R.id.navigation_offers -> alertDialog.show()
                R.id.navigation_job -> alertDialog.show()
                R.id.navigation_profile -> setCurrentFragment(profileFragment)
            }
            true //returns true value
        }

        setupView()
    }

    private fun setupView() {
        supportActionBar?.hide()
    }

    private fun setCurrentFragment(fragment: Fragment) {
        supportFragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
//        val navHostFragment =
//            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_home) as NavHostFragment
//        val navController = navHostFragment.navController

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.nav_host_fragment_activity_home, fragment)
//            addToBackStack(null)
            commitNow()
        }
    }
}