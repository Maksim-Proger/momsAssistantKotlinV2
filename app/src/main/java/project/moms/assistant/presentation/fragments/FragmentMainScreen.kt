package project.moms.assistant.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import project.moms.assistant.data.repository.sharedPreference.SharedPreferences
import project.moms.assistant.databinding.FragmentMainScreenBinding
import project.moms.assistant.presentation.OnScrollChangeListener
import project.moms.assistant.presentation.SleepViewModelFactory
import project.moms.assistant.presentation.viewModels.SleepViewModel

class FragmentMainScreen : Fragment() {
    private var _binding : FragmentMainScreenBinding? = null
    private val binding get() = _binding!!

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var viewModel: SleepViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentMainScreenBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.scrollViewContent.viewTreeObserver.addOnScrollChangedListener {
            _binding?.let {nonNullBinding -> // делаем проверку, чтобы в моменты вызова onScrollChanged() _binding не был равен null
                val maxScroll = nonNullBinding.scrollViewContent.getChildAt(0).height -
                        nonNullBinding.scrollViewContent.height
                val currentScroll = nonNullBinding.scrollViewContent.scrollY
                val percentageScrolled = currentScroll.toFloat() / maxScroll.toFloat()

                // Вызов метода onScrollChanged активности
                (activity as? OnScrollChangeListener)?.onScrollChanged(percentageScrolled)
            }
        }

        sharedPreferences = SharedPreferences(requireContext())
        viewModel = ViewModelProvider(this, SleepViewModelFactory(sharedPreferences))[SleepViewModel::class.java]

        // TODO надо выбрать наиболее приемлемый вариант
        _binding?.let {nonNullBinding ->
            lifecycleScope.launch {
                viewModel.awakeTime.collect {time ->
                    nonNullBinding.textViewTimeSinceLastSleep.text = time.toString()
                }
            }
        }
        // аналогичная запись
//        lifecycleScope.launch {
//            viewModel.awakeTime.collect { time ->
//                binding?.textViewTimeSinceLastSleep?.text = time.toString()
//            }
//        }
        // либо так
//        _binding?.let { nonNullBinding ->
//            lifecycleScope.launch {
//                viewModel.awakeTime.collect { time ->
//                    if (isAdded && nonNullBinding.textViewTimeSinceLastSleep.isActive) {
//                        nonNullBinding.textViewTimeSinceLastSleep.text = time.toString()
//                    }
//                }
//            }
//        }
        // либо так
//        _binding?.let { nonNullBinding ->
//            lifecycleScope.launch {
//                viewModel.awakeTime.collect { time ->
//                    // Проверяем, что фрагмент все еще активен
//                    if (isActive) {
//                        nonNullBinding.textViewTimeSinceLastSleep.text = time
//                    }
//                }
//            }
//        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}