package project.moms.assistant.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import project.moms.assistant.R
import project.moms.assistant.data.models.DiaryRecordingEntity

class DiaryAdapter : ListAdapter<DiaryRecordingEntity, DiaryAdapter.DiaryViewHolder>(DiffCallbackDiary()) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DiaryAdapter.DiaryViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_recycler_diary, parent, false)
        return DiaryViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: DiaryAdapter.DiaryViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }

    inner class DiaryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val itemDiary: AppCompatTextView = itemView.findViewById(R.id.item_diary)
        fun bind(diaryRecordingEntity: DiaryRecordingEntity) {
            itemDiary.text = diaryRecordingEntity.diaryRecording
        }
    }

}

class DiffCallbackDiary : DiffUtil.ItemCallback<DiaryRecordingEntity>() {
    override fun areItemsTheSame(oldItem: DiaryRecordingEntity, newItem: DiaryRecordingEntity): Boolean {
        return oldItem.diaryRecording == newItem.diaryRecording
    }

    override fun areContentsTheSame(oldItem: DiaryRecordingEntity, newItem: DiaryRecordingEntity): Boolean {
        return oldItem == newItem
    }

}