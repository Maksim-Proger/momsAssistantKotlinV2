package project.moms.assistant.presentation.unit

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import project.moms.assistant.MainActivity
import project.moms.assistant.R
import project.moms.assistant.databinding.ActivitySplashScreenBinding

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {
    private var _binding : ActivitySplashScreenBinding? = null
    private val binding get() = _binding!!

    private val splashTimeOut: Long = 2_000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val animation = AnimationUtils.loadAnimation(this, R.anim.splash_animation)
        binding.imageView.startAnimation(animation)

        lifecycleScope.launch {
            delay(splashTimeOut)  // Используем delay для асинхронной задержки
            val intent = Intent(this@SplashScreenActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}