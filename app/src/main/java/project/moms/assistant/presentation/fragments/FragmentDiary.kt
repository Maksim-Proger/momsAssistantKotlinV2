package project.moms.assistant.presentation.fragments

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import project.moms.assistant.R
import project.moms.assistant.data.repository.App
import project.moms.assistant.databinding.FragmentDiaryBinding
import project.moms.assistant.presentation.adapters.DiaryAdapter
import project.moms.assistant.presentation.viewModels.DatabaseViewModel


class FragmentDiary : Fragment() {

    private var _binding : FragmentDiaryBinding? = null
    private val binding get() = _binding!!

    private val viewModel: DatabaseViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val statisticsDao = (requireContext().applicationContext as App).db.statisticsDao()
                return DatabaseViewModel(statisticsDao) as T
            }
        }
    }

    private val adapter by lazy { DiaryAdapter() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentDiaryBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerViewDiary.adapter = adapter
        showAllEntries()
        listenerButtons()
    }

    private fun listenerButtons() {
        binding.addNewEntry.setOnClickListener {
            showAddNoteDialog()
        }
    }

    private fun showAddNoteDialog() {
        val editText = EditText(requireContext())
        val dialog = AlertDialog.Builder(requireContext())
            .setTitle("Добавить запись")
            .setView(editText)
            .setPositiveButton("Добавить") {dialog, _ ->
                val note = editText.text.toString().trim()
                if (note.isNotEmpty()) {
                    viewModel.onSaveDiaryEntry(note)
                }
                dialog.dismiss()
            }
            .setNegativeButton("Отмена") {dialog, _ ->
                dialog.dismiss()
            }
            .create()
        dialog.show()
    }

    private fun showAllEntries() {
        lifecycleScope.launch {
            viewModel.allDiaryEntries.collect {list ->
                adapter.submitList(list)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}