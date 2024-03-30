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
            navController.navigate(R.id.action_fragmentMainScreen_to_fragmentSleep)
        }
        binding.diaryButton.setOnClickListener {

        }
        binding.assistantButton.setOnClickListener {

        }
        binding.homeButton.setOnClickListener {
            navController.navigate(R.id.action_fragmentSleep_to_fragmentMainScreen)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    override fun onScrollChanged(percentageScrolled: Float) {
        BottomPanelAnimator.animateBottomPanel(binding.linearLayoutButtons, percentageScrolled)
    }
}