package project.moms.assistant.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import project.moms.assistant.R
import project.moms.assistant.data.repository.models.SleepRecording

class StatisticsAdapter
    : ListAdapter<SleepRecording, StatisticsAdapter.SleepViewHolder>(DiffCallback()) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): StatisticsAdapter.SleepViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_recycler_statistics, parent, false)
        return SleepViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: StatisticsAdapter.SleepViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }

    inner class SleepViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val itemStatistics: AppCompatTextView = itemView.findViewById(R.id.item_statistics)
        fun bind(sleepRecording: SleepRecording) {
            itemStatistics.text = sleepRecording.sleepRecordingId
        }
    }
}

class DiffCallback : DiffUtil.ItemCallback<SleepRecording>() {
    override fun areItemsTheSame(oldItem: SleepRecording, newItem: SleepRecording): Boolean {
        return oldItem.sleepRecordingId == newItem.sleepRecordingId
    }

    override fun areContentsTheSame(oldItem: SleepRecording, newItem: SleepRecording): Boolean {
        return oldItem == newItem
    }

}