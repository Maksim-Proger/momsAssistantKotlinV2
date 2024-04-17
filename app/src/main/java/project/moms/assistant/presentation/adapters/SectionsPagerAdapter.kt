package project.moms.assistant.presentation.adapters

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import project.moms.assistant.R
import project.moms.assistant.presentation.fragments.FragmentSleep
import project.moms.assistant.presentation.fragments.FragmentSleepStatistics

private val TAB_TITLES = arrayOf(
    R.string.sleep_monitoring,
    R.string.sleep_statistic
)

class SectionsPagerAdapter(private val context: Context, fm: FragmentManager) :
    FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        return if (position == 0) {
            FragmentSleep()
        } else {
            FragmentSleepStatistics()
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return context.resources.getString(TAB_TITLES[position])
    }

    override fun getCount(): Int {
        return 2
    }
}