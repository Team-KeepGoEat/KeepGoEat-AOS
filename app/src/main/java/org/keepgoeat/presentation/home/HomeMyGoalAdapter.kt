package org.keepgoeat.presentation.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.keepgoeat.R
import org.keepgoeat.databinding.ItemAddGoalBinding
import org.keepgoeat.databinding.ItemMyGoalBinding
import org.keepgoeat.presentation.home.MyGoalInfo
import org.keepgoeat.presentation.type.HomeBtnType
import org.keepgoeat.presentation.type.HomeGoalType
import org.keepgoeat.presentation.type.HomeGoalViewType
import org.keepgoeat.util.ItemDiffCallback
import org.keepgoeat.util.setVisibility

class HomeMyGoalAdapter : ListAdapter<MyGoalInfo, RecyclerView.ViewHolder>(
    ItemDiffCallback<MyGoalInfo>(
        onContentsTheSame = { old, new -> old == new },
        // TODO Response에서 받아올 때는 목표별 고유 아이디 값으로 바꾸기
        onItemsTheSame = { old, new -> old.goalName == new.goalName }
    )
) {
    private lateinit var inflater: LayoutInflater

    class MyGoalViewHolder(
        private val binding: ItemMyGoalBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        var layout = binding
        fun bind(myGoal: MyGoalInfo, goalType: HomeGoalType) {
            val btnType: HomeBtnType = if (goalType == HomeGoalType.MORE) { // 더 먹기인 경우
                if (myGoal.goalAchieved) {
                    HomeBtnType.PLUS_ACHIEVED
                } else {
                    HomeBtnType.PLUS_NOT_ACHIEVED
                }
            } else { // 덜 먹기인 경우
                if (myGoal.goalAchieved) {
                    HomeBtnType.MINUS_ACHIEVED
                } else {
                    HomeBtnType.MINUS_NOT_ACHIEVED
                }
            }
            binding.goal = myGoal
            binding.goalType = goalType
            binding.goalBtn = btnType
        }
    }

    class AddGoalViewHolder(
        private val binding: ItemAddGoalBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        var layout = binding
        fun bind(goalCount: Int) {
            when (goalCount) {
                0 -> {}
                1 -> binding.tvAddMoreGoal.setText(R.string.home_two_more_goal)
                2 -> binding.tvAddMoreGoal.setText(R.string.home_one_more_goal)
                3 -> {
                    binding.tvAddMoreGoal.setText(R.string.home_no_more_goal)
                    binding.btnMakeGoal.setVisibility(false)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (!::inflater.isInitialized)
            inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            HomeGoalViewType.MY_GOAL_TYPE.goalType -> {
                MyGoalViewHolder(ItemMyGoalBinding.inflate(inflater, parent, false))
            }
            HomeGoalViewType.ADD_GOAL_TYPE.goalType -> {
                AddGoalViewHolder(ItemAddGoalBinding.inflate(inflater, parent, false))
            }
            else -> {
                throw java.lang.ClassCastException("Unknown ViewType Error")
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is MyGoalViewHolder -> {
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
            is AddGoalViewHolder -> holder.bind(currentList.size - 1)
        }
    }

    override fun getItemViewType(position: Int): Int = currentList[position].type.goalType
}
