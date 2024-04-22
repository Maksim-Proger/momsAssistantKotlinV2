package project.moms.assistant.presentation.fragments

import android.os.Bundle
import android.transition.ChangeBounds
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.fragment.app.Fragment
import project.moms.assistant.R
import project.moms.assistant.databinding.FragmentAssistantBinding

// TODO доработать функционал, чтобы вместе с потерей фокуса скрывалась и клавиатура
class FragmentAssistant : Fragment() {
    private var _binding : FragmentAssistantBinding? = null
    private val binding get() = _binding!!

    private lateinit var constraintLayout: ConstraintLayout
    private lateinit var constraintSetStart: ConstraintSet
    private lateinit var constraintSetEnd: ConstraintSet

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentAssistantBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // region Инициализируем ConstraintSets
        constraintLayout = binding.mainConstraintLayout
        constraintSetStart = ConstraintSet()
        constraintSetStart.clone(constraintLayout)
        constraintSetEnd = ConstraintSet()
        constraintSetEnd.clone(requireContext(), R.layout.fragment_assistant_expanded)
        // endregion
        // region фукус и анимация
        binding.textInputEditText.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                expandEditText()
            }
        }
        binding.mainConstraintLayout.setOnClickListener {
            onEmptyAreaClicked()
        }
        // endregion
    }

    private fun expandEditText() {
        TransitionManager.beginDelayedTransition(constraintLayout, ChangeBounds().apply {
            duration = 300
            interpolator = AccelerateDecelerateInterpolator()
        })
        constraintSetEnd.applyTo(constraintLayout)
    }

    private fun collapseEditText() {
        TransitionManager.beginDelayedTransition(constraintLayout, ChangeBounds().apply {
            duration = 300
            interpolator = AccelerateDecelerateInterpolator()
        })
        constraintSetStart.applyTo(constraintLayout)
        binding.textInputEditText.clearFocus() // Сброс фокуса, чтобы клавиатура скрылась
    }

    private fun onEmptyAreaClicked() {
        collapseEditText()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}