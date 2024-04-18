package project.moms.assistant.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import project.moms.assistant.databinding.FragmentSleepBinding
import project.moms.assistant.presentation.OnScrollChangeListener

class FragmentSleep : Fragment() {
    private var _binding : FragmentSleepBinding? = null
    private val binding : FragmentSleepBinding
        get() {
            return _binding!!
        }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentSleepBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.scrollViewContent.viewTreeObserver.addOnScrollChangedListener {
            if (_binding != null) { // делаем проверку, чтобы в моменты вызова onScrollChanged() _binding не был равен null
                val maxScroll = binding.scrollViewContent.getChildAt(0).height -
                        binding.scrollViewContent.height
                val currentScroll = binding.scrollViewContent.scrollY
                val percentageScrolled = currentScroll.toFloat() / maxScroll.toFloat()

                // Вызов метода onScrollChanged активности
                (activity as? OnScrollChangeListener)?.onScrollChanged(percentageScrolled) // TODO детально разобраться эту строку
            }
        }
    }

    private fun currentTime() {

    }

    private fun timeDifference() {

    }

    private fun checkState() {

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}