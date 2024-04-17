package project.moms.assistant.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import project.moms.assistant.databinding.FragmentSelectionPagerBinding
import project.moms.assistant.presentation.adapters.SectionsPagerAdapter

class FragmentSectionsPager : Fragment() {
    private var _binding : FragmentSelectionPagerBinding? = null
    private val binding get() = _binding!!
    private val sectionsPagerAdapter by lazy {
        SectionsPagerAdapter(requireContext(), requireActivity().supportFragmentManager)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentSelectionPagerBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewPager: ViewPager = binding.viewPaper
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = binding.tabs
        tabs.setupWithViewPager(viewPager)
    }
}