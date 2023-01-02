package org.keepgoeat.presentation.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.keepgoeat.databinding.ItemMyGoalBinding
import org.keepgoeat.presentation.home.HomeBtnType
import org.keepgoeat.presentation.home.HomeGoalType
import org.keepgoeat.presentation.home.MyGoalInfo

class HomeMyGoalAdapter : ListAdapter<MyGoalInfo, HomeMyGoalAdapter.MyGoalViewHolder>(
    MyGoalDiffCallback
) {
    class MyGoalViewHolder(
        private val binding: ItemMyGoalBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        var layout = binding
        fun bind(myGoal: MyGoalInfo, goalType: HomeGoalType) {
            binding.goal = myGoal
            binding.goalType = goalType
            val btnType: HomeBtnType = if (goalType == HomeGoalType.MORE) {//더 먹기인 경우
                if (myGoal.goalAchieved) {
                    HomeBtnType.PLUS_ACHIEVED
                } else {
                    HomeBtnType.PLUS_NOT_ACHIEVED
                }
            } else {//덜 먹기인 경우
                if (myGoal.goalAchieved) {
                    HomeBtnType.MINUS_ACHIEVED
                } else {
                    HomeBtnType.MINUS_NOT_ACHIEVED
                }
            }
            binding.goalBtn = btnType
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

        holder.layout.btnGoal.setOnClickListener {
            currentList[position].goalAchieved = !currentList[position].goalAchieved
            notifyItemChanged(position)
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
