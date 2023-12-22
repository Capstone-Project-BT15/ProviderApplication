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
import androidx.viewpager.widget.ViewPager
import com.airbnb.lottie.LottieAnimationView
import corp.jasane.provider.databinding.ActivityHomeBinding
import corp.jasane.provider.modules.home.ui.ui.addJob.AddJobFragment
import corp.jasane.provider.modules.home.ui.ui.home.HomeFragment
import corp.jasane.provider.modules.home.ui.ui.job.JobFragment
import corp.jasane.provider.modules.home.ui.ui.offers.ui.OffersFragment
import corp.jasane.provider.modules.home.ui.ui.profile.ProfileFragment
import androidx.fragment.app.FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var viewPager: ViewPager
    private lateinit var bottomNavigationView: BottomNavigationView

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

        val homeFragment = HomeFragment()
        val profileFragment = ProfileFragment()
        val offersFragment = OffersFragment()
        val jobFragment = JobFragment()
        val addJobFragment = AddJobFragment()

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
            setCurrentFragment(jobFragment)
        }

//        val pagerAdapter = HomeActivityViewPagerAdapter(
//            supportFragmentManager,
//            listOf(homeFragment, addJobFragment, offersFragment, jobFragment, profileFragment)
//        )
//        binding.viewPager.adapter = pagerAdapter

//        val homeFragment = HomeFragment()
//        val profileFragment = ProfileFragment()
//        val offersFragment = OffersFragment()
//        val jobFragment = JobFragment()
//        val addJobFragment = AddJobFragment()

        setCurrentFragment(homeFragment)

        val bottomNavigation: BottomNavigationView = binding.navView
        bottomNavigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_home -> setCurrentFragment(homeFragment)
                R.id.navigation_add_job -> setCurrentFragment(addJobFragment)
                R.id.navigation_offers -> setCurrentFragment(offersFragment)
                R.id.navigation_job -> alertDialog.show()
                R.id.navigation_profile -> setCurrentFragment(profileFragment)
            }
            true
        }

        setupView()
    }

    private fun setupView() {
        supportActionBar?.hide()
    }

    private fun setCurrentFragment(fragment: Fragment) {
        if (!supportFragmentManager.isStateSaved) {
            supportFragmentManager.popBackStackImmediate(
                null,
                FragmentManager.POP_BACK_STACK_INCLUSIVE
            )
//        val navHostFragment =
//            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_home) as NavHostFragment
//        val navController = navHostFragment.navController

            supportFragmentManager.beginTransaction().apply {
                replace(R.id.container, fragment)
                addToBackStack(null)
                commitAllowingStateLoss()
            }
        }
    }
}