package project.moms.assistant.presentation

import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import project.moms.assistant.R

// TODO подписать назначение данного класса
object BottomPanelAnimator {
    fun animateBottomPanel(
        linearLayoutButtons: LinearLayout,
        percentageScrolled: Float
    ) {
        val initialPanelWidth = linearLayoutButtons
            .resources.getDimensionPixelSize(R.dimen.initial_panel_width)
        val newPanelWidth = initialPanelWidth + (percentageScrolled *
                (linearLayoutButtons.resources.getDimensionPixelSize(R.dimen.max_panel_width) - initialPanelWidth)).toInt()

        val layoutParams = linearLayoutButtons.layoutParams
        layoutParams.width = newPanelWidth
        linearLayoutButtons.layoutParams = layoutParams

        val color = if (percentageScrolled >= 1.0f) {
            ContextCompat.getColor(linearLayoutButtons.context, R.color.background_color)
        } else {
            ContextCompat.getColor(linearLayoutButtons.context, R.color.background_color)
        }

        linearLayoutButtons.setBackgroundColor(color)
    }
}