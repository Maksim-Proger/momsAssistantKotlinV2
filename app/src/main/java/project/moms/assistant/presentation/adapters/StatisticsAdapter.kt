package project.moms.assistant.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatTextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import project.moms.assistant.R
import project.moms.assistant.data.models.SleepRecordingEntity
import java.text.SimpleDateFormat
import java.util.Locale

class StatisticsAdapter
    : ListAdapter<SleepRecordingEntity, StatisticsAdapter.SleepViewHolder>(DiffCallback()) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): StatisticsAdapter.SleepViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_recycler_statistics, parent, false)
        return SleepViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: StatisticsAdapter.SleepViewHolder, position: Int) {
        val currentItem = getItem(position)
        val isFirstInGroup = if (position > 0) {
            currentItem.date != getItem(position - 1).date
        } else {
            true
        }
        holder.bind(currentItem, isFirstInGroup)
    }

    inner class SleepViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val itemStatistics: AppCompatTextView = itemView.findViewById(R.id.item_statistics)
        private val itemDate: AppCompatTextView = itemView.findViewById(R.id.textViewDate)
        private val cardView: CardView = itemView.findViewById(R.id.my_card_view)
        fun bind(sleepRecordingEntity: SleepRecordingEntity, isFirstInGroup: Boolean) {
            itemStatistics.text = sleepRecordingEntity.sleepRecordingId

            // Преобразуем строку с датой в объект Date
            val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
            val date = dateFormat.parse(sleepRecordingEntity.date)

            val resDate = date?.let { SimpleDateFormat("dd MMMM", Locale.getDefault()).format(it) }

            // Устанавливаем дату только для первой записи в группе
            if (isFirstInGroup) {
                itemDate.text = resDate
                itemDate.visibility = View.VISIBLE
                val cardLayoutParams = cardView.layoutParams as LinearLayout.LayoutParams
                cardLayoutParams.setMargins(0, 20, 0, 20)
                cardView.layoutParams = cardLayoutParams
            } else {
                itemDate.visibility = View.GONE
                val cardLayoutParams = cardView.layoutParams as LinearLayout.LayoutParams
                cardLayoutParams.setMargins(0, 0, 0, 0)
                cardView.layoutParams = cardLayoutParams
            }
        }
    }
}

class DiffCallback : DiffUtil.ItemCallback<SleepRecordingEntity>() {
    override fun areItemsTheSame(
        oldItem: SleepRecordingEntity,
        newItem: SleepRecordingEntity
    ): Boolean {
        return oldItem.sleepRecordingId == newItem.sleepRecordingId
    }

    override fun areContentsTheSame(
        oldItem: SleepRecordingEntity,
        newItem: SleepRecordingEntity
    ): Boolean {
        return oldItem == newItem
    }

}