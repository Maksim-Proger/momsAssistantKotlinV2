package project.moms.assistant.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.launch
import project.moms.assistant.data.repository.App
import project.moms.assistant.databinding.FragmentSleepStatisticsBinding
import project.moms.assistant.presentation.adapters.StatisticsAdapter
import project.moms.assistant.presentation.viewModels.DatabaseViewModel

class FragmentSleepStatistics : Fragment() {
    private var _binding : FragmentSleepStatisticsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: DatabaseViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val statisticsDao = (requireContext().applicationContext as App).db.statisticsDao()
                return DatabaseViewModel(statisticsDao) as T
            }
        }
    }

    private val adapter by lazy { StatisticsAdapter() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentSleepStatisticsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerViewStatistics.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewStatistics.adapter = adapter
        showAllEntries()
    }

    private fun showAllEntries() {
        lifecycleScope.launch {
            viewModel.allEntries.collect {list ->
                adapter.submitList(list)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}