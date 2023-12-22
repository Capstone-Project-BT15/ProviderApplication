//package corp.jasane.provider.modules.home.ui
//
//import androidx.fragment.app.Fragment
//import androidx.fragment.app.FragmentActivity
//import androidx.fragment.app.FragmentManager
//import androidx.fragment.app.FragmentPagerAdapter
//import androidx.fragment.app.FragmentStatePagerAdapter
//import androidx.viewpager2.adapter.FragmentStateAdapter
//import androidx.lifecycle.Lifecycle
//
//
//class HomeActivityViewPagerAdapter(fragmentManager: FragmentManager, private val fragments: List<Fragment>) :
//    FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
//
//    override fun getItem(position: Int): Fragment {
//        return fragments[position]
//    }
//
//    override fun getCount(): Int {
//        return fragments.size
//    }
//}