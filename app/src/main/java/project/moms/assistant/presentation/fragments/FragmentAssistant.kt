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
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.launch
import project.moms.assistant.R
import project.moms.assistant.data.repository.ChatRepository
import project.moms.assistant.databinding.FragmentAssistantBinding
import project.moms.assistant.presentation.ChatViewModelFactory
import project.moms.assistant.presentation.adapters.ChatAdapter
import project.moms.assistant.presentation.viewModels.ChatViewModel
import android.content.Context
import android.view.inputmethod.InputMethodManager


// TODO доработать функционал, чтобы вместе с потерей фокуса скрывалась и клавиатура
class FragmentAssistant : Fragment() {
    private var _binding : FragmentAssistantBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ChatViewModel by viewModels {
        ChatViewModelFactory(ChatRepository(requireContext()))
    }

    private val adapter by lazy { ChatAdapter() }

    private lateinit var constraintLayout: ConstraintLayout
    private lateinit var constraintSetStart: ConstraintSet
    private lateinit var constraintSetEnd: ConstraintSet

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentAssistantBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getChatMessagesFlow().collect { messages ->
                adapter.submitList(messages)
            }
        }

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

        addListenerOnButton()
    }

    private fun addListenerOnButton() {
        addIconAndSendMessage()
    }

    private fun addIconAndSendMessage() {
        val searchIcon = ContextCompat.getDrawable(requireContext(), R.drawable.searched_icon)
        binding.textInputLayout.endIconMode =TextInputLayout.END_ICON_CUSTOM
        binding.textInputLayout.endIconDrawable = searchIcon

        binding.textInputLayout.setEndIconOnClickListener {
            val messageText = binding.textInputEditText.text.toString().trim()
            if (messageText.isNotEmpty()) {
                viewModel.sendMessage(messageText)
                binding.textInputEditText.setText("")
                collapseEditText()
            }
        }
    }

    private fun expandEditText() {
        TransitionManager.beginDelayedTransition(constraintLayout, ChangeBounds().apply {
            duration = 300
            interpolator = AccelerateDecelerateInterpolator()
        })
        constraintSetEnd.applyTo(constraintLayout)
    }

    private fun collapseEditText() {
        hideKeyboard()
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

    private fun hideKeyboard() {
        val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view?.windowToken, 0)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}