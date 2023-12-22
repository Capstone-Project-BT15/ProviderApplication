package corp.jasane.provider.modules.home.ui.ui.offers.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.tabs.TabLayoutMediator
import corp.jasane.provider.databinding.FragmentOffersBinding
import corp.jasane.provider.modules.home.ui.ui.offers.data.viewModel.OffersViewModel

class OffersFragment : Fragment() {

    private var _binding: FragmentOffersBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val offersViewModel =
            ViewModelProvider(this).get(OffersViewModel::class.java)

        _binding = FragmentOffersBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tabLayout = binding.tabLayout
        val viewPager2 = binding.viewPager2

        val adapter = OffersPagerAdapter(requireActivity())
        viewPager2.adapter = adapter

        viewPager2.offscreenPageLimit = 1

        TabLayoutMediator(tabLayout, viewPager2) { tab, position ->
            when (position) {
                0 -> tab.text = "Pending"
                1 -> tab.text = "Accept"
                2 -> tab.text = "Finish"
            }
        }.attach()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}