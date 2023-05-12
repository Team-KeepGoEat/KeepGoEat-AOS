package org.keepgoeat.presentation.my.archive

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import org.keepgoeat.databinding.ItemArchivedGoalBinding
import org.keepgoeat.domain.model.ArchivedGoal

class ArchivedGoalAdapter(
    private val showKeepDeleteDialog: (Int) -> Unit,
) : RecyclerView.Adapter<ArchivedGoalAdapter.ArchivedGoalViewHolder>() {
    private lateinit var inflater: LayoutInflater
    private val goalLists: MutableList<ArchivedGoal> = mutableListOf()
    private var goalWithDeleteViewVisible: ArchivedGoal? = null

    class ArchivedGoalViewHolder(private val binding: ItemArchivedGoalBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(
            data: ArchivedGoal,
            showKeepDeleteDialog: (Int) -> Unit,
            showDeleteButton: (Button, ArchivedGoal) -> Unit,
            hideDeleteButton: (Button) -> Unit,
        ) {
            binding.goal = data

            if (binding.btnArchivedGoalDelete.visibility == View.VISIBLE)
                hideDeleteButton(binding.btnArchivedGoalDelete)

            binding.ivArchivedGoalDetail.setOnClickListener {
                if (binding.btnArchivedGoalDelete.visibility == View.INVISIBLE)
                    showDeleteButton(binding.btnArchivedGoalDelete, data)
            }
            binding.btnArchivedGoalDelete.setOnClickListener {
                showKeepDeleteDialog(data.id)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArchivedGoalViewHolder {
        if (!::inflater.isInitialized)
            inflater = LayoutInflater.from(parent.context)

        return ArchivedGoalViewHolder(ItemArchivedGoalBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: ArchivedGoalViewHolder, position: Int) {
        holder.onBind(
            goalLists[position],
            showKeepDeleteDialog,
            ::showDeleteButton,
            ::hideDeleteButton
        )
    }

    override fun getItemCount() = goalLists.size

    fun setGoalList(goals: MutableList<ArchivedGoal>) {
        goalLists.clear()
        goalLists.addAll(goals)
        notifyDataSetChanged()
    }

    private fun hideDeleteButton(deleteButton: Button) {
        deleteButton.visibility = View.INVISIBLE
        goalWithDeleteViewVisible = null
    }

    private fun showDeleteButton(deleteButton: Button, goal: ArchivedGoal) {
        deleteButton.visibility = View.VISIBLE
        goalWithDeleteViewVisible = goal
    }

    fun checkForVisibleDeleteButton() {
        if (goalWithDeleteViewVisible == null) return
        else notifyItemChanged(goalLists.indexOf(goalWithDeleteViewVisible))
    }

    fun removeGoal(goalId: Int) {
        val goal = goalLists.find { it.id == goalId }
        notifyItemRemoved(goalLists.indexOf(goal))
    }
}
