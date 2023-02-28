package org.keepgoeat.presentation.my

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.keepgoeat.databinding.ItemAchievedGoalBinding
import org.keepgoeat.domain.model.AchievedGoal
import org.keepgoeat.util.ItemDiffCallback

class AchievedGoalAdapter :
    ListAdapter<AchievedGoal, AchievedGoalAdapter.AchievedGoalViewHolder>(
        ItemDiffCallback<AchievedGoal>(
            onContentsTheSame = { old, new -> old == new },
            onItemsTheSame = { old, new -> old.id == new.id }
        )
    ) {
    private lateinit var inflater: LayoutInflater


    inner class AchievedGoalViewHolder(private val binding: ItemAchievedGoalBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: AchievedGoal, context: Context) {
            binding.goal = data
            binding.ivAchievedGoalDetail.setOnClickListener {
                if (binding.btnAchievedGoalDelete.visibility == View.GONE)
                    binding.btnAchievedGoalDelete.visibility = View.VISIBLE
            }
            binding.btnAchievedGoalDelete.setOnClickListener {
                KeepDeleteDialogFragment().show((context as AchievedGoalActivity).supportFragmentManager, "KeepDeleteDialog")
                listener.updateGoalId(data)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AchievedGoalViewHolder {
        if (!::inflater.isInitialized)
            inflater = LayoutInflater.from(parent.context)

        return AchievedGoalViewHolder(ItemAchievedGoalBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: AchievedGoalViewHolder, position: Int) {
        holder.onBind(currentList[position], context)
    }
}
