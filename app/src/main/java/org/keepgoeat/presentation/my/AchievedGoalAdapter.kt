package org.keepgoeat.presentation.my

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import org.keepgoeat.databinding.ItemAchievedGoalBinding
import org.keepgoeat.domain.model.AchievedGoal

class AchievedGoalAdapter(
    private val showKeepDeleteDialog: (Int) -> Unit,
) : RecyclerView.Adapter<AchievedGoalAdapter.AchievedGoalViewHolder>() {
    private lateinit var inflater: LayoutInflater
    private var keepGoalList: MutableList<AchievedGoal> = mutableListOf()
    private var goalWithDeleteViewVisible: AchievedGoal? = null

    class AchievedGoalViewHolder(private val binding: ItemAchievedGoalBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(
            data: AchievedGoal,
            showKeepDeleteDialog: (Int) -> Unit,
            showDeleteButton: (Button, AchievedGoal) -> Unit,
            hideDeleteButton: (Button) -> Unit
        ) {
            binding.goal = data

            if (binding.btnAchievedGoalDelete.visibility == View.VISIBLE)
                hideDeleteButton(binding.btnAchievedGoalDelete)

            binding.ivAchievedGoalDetail.setOnClickListener {
                if (binding.btnAchievedGoalDelete.visibility == View.INVISIBLE)
                    showDeleteButton(binding.btnAchievedGoalDelete, data)
            }
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
            showKeepDeleteDialog,
            ::showDeleteButton,
            ::hideDeleteButton
        )
    }

    override fun getItemCount() = keepGoalList.size

    fun setList(goalList: MutableList<AchievedGoal>) {
        this.keepGoalList = goalList.toMutableList()
        notifyDataSetChanged()
    }

    fun hideDeleteButton(deleteButton: Button) {
        deleteButton.visibility = View.INVISIBLE
        goalWithDeleteViewVisible = null
    }

    fun showDeleteButton(deleteButton: Button, goal: AchievedGoal) {
        deleteButton.visibility = View.VISIBLE
        goalWithDeleteViewVisible = goal
    }

    fun checkForVisibleDeleteButton() {
        if (goalWithDeleteViewVisible == null) return
        else
            notifyItemChanged(keepGoalList.indexOf(goalWithDeleteViewVisible))
    }

    fun removeGoal(goalId: Int) {
        val goal = keepGoalList.find { it.id == goalId }
        notifyItemRemoved(keepGoalList.indexOf(goal))
    }
}
