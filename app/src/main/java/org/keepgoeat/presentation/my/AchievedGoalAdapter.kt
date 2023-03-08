package org.keepgoeat.presentation.my

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.keepgoeat.databinding.ItemAchievedGoalBinding
import org.keepgoeat.domain.model.AchievedGoal

class AchievedGoalAdapter(
    private val showKeepDeleteDialog: (Int) -> Unit,
) : RecyclerView.Adapter<AchievedGoalAdapter.AchievedGoalViewHolder>() {
    private lateinit var inflater: LayoutInflater
    private var keepGoalList: MutableList<AchievedGoal> = mutableListOf()

    class AchievedGoalViewHolder(private val binding: ItemAchievedGoalBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(
            data: AchievedGoal,
            showKeepDeleteDialog: (Int) -> Unit,
        ) {
            binding.goal = data

            binding.btnAchievedGoalDelete.setOnClickListener {
                showKeepDeleteDialog(data.id)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AchievedGoalViewHolder {
        if (!::inflater.isInitialized)
            inflater = LayoutInflater.from(parent.context)

        return AchievedGoalViewHolder(ItemAchievedGoalBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: AchievedGoalViewHolder, position: Int) {
        holder.onBind(
            keepGoalList[position],
            showKeepDeleteDialog
        )
    }

    override fun getItemCount() = keepGoalList.size

    fun setList(goalList: MutableList<AchievedGoal>) {
        this.keepGoalList = goalList.toMutableList()
        notifyDataSetChanged()
    }

    fun removeGoal(goalId: Int) {
        val goal = keepGoalList.find { it.id == goalId }
        notifyItemRemoved(keepGoalList.indexOf(goal))
    }

}
