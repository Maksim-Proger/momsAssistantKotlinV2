package project.moms.assistant.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import project.moms.assistant.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, FragmentMainScreen.newInstance())
                .commitNow()
        }
    }
}