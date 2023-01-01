package org.keepgoeat.presentation.home.adapter

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.keepgoeat.databinding.ItemMyGoalBinding
import org.keepgoeat.presentation.home.HomeGoalType
import org.keepgoeat.presentation.home.MyGoalInfo

class HomeMyGoalAdapter : ListAdapter<MyGoalInfo, HomeMyGoalAdapter.MyGoalViewHolder>(
    MyGoalDiffCallback
) {
    class MyGoalViewHolder(
        private val binding: ItemMyGoalBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(myGoal: MyGoalInfo, goalType: HomeGoalType) {
            binding.goal = myGoal
            binding.goalType = goalType
            binding.imgGoalTag.setImageResource(goalType.tagDrawableRes)
            binding.layoutGoalTag.backgroundTintList = ColorStateList.valueOf(goalType.tagColorRes)
            binding.tvGoalName.setTextColor(goalType.textColorRes)
            binding.tvItemGoalDate.setTextColor(goalType.textColorRes)
            binding.btnGoal.setBackgroundColor(goalType.btnColorRes)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyGoalViewHolder {
        val binding =
            ItemMyGoalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyGoalViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyGoalViewHolder, position: Int) {
        if (currentList[position].moreGoal) {
            holder.bind(currentList[position], HomeGoalType.MORE)
        } else {
            holder.bind(currentList[position], HomeGoalType.LESS)
        }
    }
}

object MyGoalDiffCallback : DiffUtil.ItemCallback<MyGoalInfo>() {
    override fun areItemsTheSame(oldItem: MyGoalInfo, newItem: MyGoalInfo): Boolean {
        return oldItem.goalName == newItem.goalName
    }

    override fun areContentsTheSame(oldItem: MyGoalInfo, newItem: MyGoalInfo): Boolean {
        return oldItem == newItem
    }
}