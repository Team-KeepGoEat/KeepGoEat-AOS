package org.keepgoeat.presentation.my

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.keepgoeat.databinding.ItemAchievedGoalBinding
import org.keepgoeat.domain.model.AchievedGoal
import org.keepgoeat.presentation.type.EatingType
import org.keepgoeat.util.ItemDiffCallback

class AchievedGoalAdapter :
    ListAdapter<AchievedGoal, AchievedGoalAdapter.MyGoalViewHolder>(
        ItemDiffCallback<AchievedGoal>(
            onContentsTheSame = { old, new -> old == new },
            onItemsTheSame = { old, new -> old.id == new.id }
        )
    ) {
    private lateinit var inflater: LayoutInflater

    class MyGoalViewHolder(private val binding: ItemAchievedGoalBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(
            data: AchievedGoal,
            eatingType: EatingType,
        ) {
            binding.goal = data
            binding.eatingType = eatingType
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyGoalViewHolder {
        if (!::inflater.isInitialized)
            inflater = LayoutInflater.from(parent.context)

        return MyGoalViewHolder(ItemAchievedGoalBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: MyGoalViewHolder, position: Int) {
        if (currentList[position].isMore) {
            holder.onBind(currentList[position], EatingType.MORE)
        } else {
            holder.onBind(currentList[position], EatingType.LESS)
        }
    }
}
