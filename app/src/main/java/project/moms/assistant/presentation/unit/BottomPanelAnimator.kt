package project.moms.assistant.presentation.unit

import android.content.Context
import android.widget.ImageButton
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import project.moms.assistant.R

object BottomPanelAnimator {
    fun animateBottomPanel(
        context: Context,
        linearLayoutButtons: LinearLayout,
        childInLinearLayout: Int,
        buttons: List<ImageButton>,
        percentageScrolled: Float
    ) {
        val initialPanelWidth = linearLayoutButtons
            .resources.getDimensionPixelSize(R.dimen.initial_panel_width)
        val newPanelWidth = initialPanelWidth + (percentageScrolled *
                (linearLayoutButtons.resources.getDimensionPixelSize(R.dimen.max_panel_width) - initialPanelWidth)).toInt()

        val layoutParams = linearLayoutButtons.layoutParams
        layoutParams.width = newPanelWidth
        linearLayoutButtons.layoutParams = layoutParams

        if (percentageScrolled >= 1.0f) {
            val color =
                ContextCompat.getColor(linearLayoutButtons.context, R.color.background_color)
            linearLayoutButtons.setBackgroundColor(color)
            for (i in 0 until childInLinearLayout) {
                val childView = linearLayoutButtons.getChildAt(i)
                childView.setBackgroundColor(color)
            }
            buttons.forEach { button ->
                val drawable = button.drawable
                DrawableCompat.setTint(
                    drawable,
                    ContextCompat.getColor(context, R.color.text_color)
                )
                button.setImageDrawable(drawable)
            }
        } else {
            linearLayoutButtons.setBackgroundResource(R.drawable.rounded_corners)
            for (i in 0 until childInLinearLayout) {
                val childView = linearLayoutButtons.getChildAt(i)
                childView.setBackgroundResource(R.drawable.rounded_corners)
            }
            buttons.forEach { button ->
                val drawable = button.drawable
                DrawableCompat.setTint(
                    drawable,
                    ContextCompat.getColor(context, R.color.text_color2)
                )
                button.setImageDrawable(drawable)
            }
        }
    }
}