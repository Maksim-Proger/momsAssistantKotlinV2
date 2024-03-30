package project.moms.assistant.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
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
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
    }

    private fun setupListener() {
        binding.sleepButton.setOnClickListener {
            val currentDestination = navController.currentDestination?.id
            when (currentDestination) {
                R.id.fragmentMainScreen ->
                    navController.navigate(R.id.action_fragmentMainScreen_to_fragmentSleep)
                R.id.fragmentDiary ->
                    navController.navigate(R.id.action_fragmentDiary_to_fragmentSleep)
                R.id.fragmentAssistant ->
                    navController.navigate(R.id.action_fragmentAssistant_to_fragmentSleep)
            }
        }
        binding.diaryButton.setOnClickListener {
            val currentDestination = navController.currentDestination?.id
            when (currentDestination) {
                R.id.fragmentMainScreen ->
                    navController.navigate(R.id.action_fragmentMainScreen_to_fragmentDiary)
                R.id.fragmentSleep ->
                    navController.navigate(R.id.action_fragmentSleep_to_fragmentDiary)
                R.id.fragmentAssistant ->
                    navController.navigate(R.id.action_fragmentAssistant_to_fragmentDiary)
            }
        }
        binding.assistantButton.setOnClickListener {
            val currentDestination = navController.currentDestination?.id
            when (currentDestination) {
                R.id.fragmentMainScreen ->
                    navController.navigate(R.id.action_fragmentMainScreen_to_fragmentAssistant)
                R.id.fragmentSleep ->
                    navController.navigate(R.id.action_fragmentSleep_to_fragmentAssistant)
                R.id.fragmentDiary ->
                    navController.navigate(R.id.action_fragmentDiary_to_fragmentAssistant)
            }
        }
        binding.homeButton.setOnClickListener {
            val currentDestination = navController.currentDestination?.id
            when (currentDestination) {
                R.id.fragmentSleep ->
                    navController.navigate(R.id.action_fragmentSleep_to_fragmentMainScreen)
                R.id.fragmentDiary ->
                    navController.navigate(R.id.action_fragmentDiary_to_fragmentMainScreen)
                R.id.fragmentAssistant ->
                    navController.navigate(R.id.action_fragmentAssistant_to_fragmentMainScreen)
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
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