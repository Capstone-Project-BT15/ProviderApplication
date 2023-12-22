package corp.jasane.provider.modules.home.ui.ui.offers.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class OffersPagerAdapter(private var fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> OffersStatusOfferFragment.newInstance("Pending")
            1 -> OffersStatusAcceptedFragment.newInstance("Accept")
            2 -> OffersStatusFinishFragment.newInstance("Finish")
            else -> throw IllegalArgumentException("Invalid position")
        }
    }
}