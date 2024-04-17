package project.moms.assistant.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import project.moms.assistant.R
import project.moms.assistant.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), OnScrollChangeListener {
    private var _binding : ActivityMainBinding? = null
    private val binding : ActivityMainBinding
        get() {
            return _binding!!
        }
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupNavigation()
        setupListener()
    }

    private fun setupNavigation() {
        val navHostFragment =
            supportFragmentManager
                .findFragmentById(R.id.nav_host_fragment_content_navigation) as NavHostFragment
        navController = navHostFragment.navController
    }

    private fun setupListener() {
        val clickListener = View.OnClickListener { view ->
            when (view.id) {
                R.id.sleep_button -> navigateWithAnimation(R.id.fragmentSleep)
                R.id.diary_button -> navigateWithAnimation(R.id.fragmentDiary)
                R.id.assistant_button -> navigateWithAnimation(R.id.fragmentAssistant)
                R.id.home_button -> navigateWithAnimation(R.id.fragmentMainScreen)
            }
        }

        // Присваиваем обработчик каждой кнопке
        binding.sleepButton.setOnClickListener(clickListener)
        binding.diaryButton.setOnClickListener(clickListener)
        binding.assistantButton.setOnClickListener(clickListener)
        binding.homeButton.setOnClickListener(clickListener)
    }

    private fun navigateWithAnimation(destinationId: Int) {
        val navOptions = NavOptions.Builder()
            .setEnterAnim(R.anim.slide_in_right) // Анимация входа нового фрагмента
            .setExitAnim(R.anim.slide_out_left) // Анимация выхода текущего фрагмента
            .setPopEnterAnim(R.anim.slide_in_left) // Анимация входа текущего фрагмента при возвращении
            .setPopExitAnim(R.anim.slide_out_right) // Анимация выхода нового фрагмента при возвращении
            .build()

        navController.navigate(destinationId, null, navOptions)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_navigation)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    override fun onScrollChanged(percentageScrolled: Float) {
        val childInLinearLayout = binding.linearLayoutButtons.childCount
        val buttons = listOf(binding.homeButton, binding.sleepButton, binding.diaryButton, binding.assistantButton)
        BottomPanelAnimator.animateBottomPanel(
            this,
            binding.linearLayoutButtons,
            childInLinearLayout,
            buttons,
            percentageScrolled
        )
    }
}