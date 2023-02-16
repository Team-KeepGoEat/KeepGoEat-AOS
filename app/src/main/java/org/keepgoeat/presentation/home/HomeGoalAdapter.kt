package org.keepgoeat.presentation.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.keepgoeat.R
import org.keepgoeat.databinding.ItemAddGoalBinding
import org.keepgoeat.databinding.ItemHomeGoalBinding
import org.keepgoeat.domain.model.HomeGoal
import org.keepgoeat.presentation.type.EatingType
import org.keepgoeat.presentation.type.HomeBtnType
import org.keepgoeat.presentation.type.HomeGoalViewType
import org.keepgoeat.util.ItemDiffCallback
import org.keepgoeat.util.extension.setOnSingleClickListener
import org.keepgoeat.util.setVisibility

class HomeGoalAdapter(
    private val changeBtnColor: (HomeGoal) -> Unit,
    private val moveToDetail: (EatingType, Int) -> Unit,
    private val showMakeGoalDialog: () -> Unit,
) : ListAdapter<HomeGoal, RecyclerView.ViewHolder>(
    ItemDiffCallback<HomeGoal>(
        onContentsTheSame = { old, new -> old == new },
        onItemsTheSame = { old, new -> old.id == new.id }
    )
) {
    private lateinit var inflater: LayoutInflater

    class MyGoalViewHolder(
        private val binding: ItemHomeGoalBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        var layout = binding
        fun bind(
            myGoal: HomeGoal,
            eatingType: EatingType,
            changeBtnColor: (HomeGoal) -> Unit,
            moveToDetail: (EatingType, Int) -> Unit,
        ) {
            val btnType: HomeBtnType = if (eatingType == EatingType.MORE) { // 더 먹기인 경우
                if (myGoal.isAchieved) {
                    HomeBtnType.PLUS_ACHIEVED
                } else {
                    HomeBtnType.PLUS_NOT_ACHIEVED
                }
            } else { // 덜 먹기인 경우
                if (myGoal.isAchieved) {
                    HomeBtnType.MINUS_ACHIEVED
                } else {
                    HomeBtnType.MINUS_NOT_ACHIEVED
                }
            }
            binding.goal = myGoal
            binding.goalType = eatingType
            binding.goalBtn = btnType

            with(binding) {
                btnGoalAchieved.setOnSingleClickListener {
                    changeBtnColor(myGoal)
                }
                layoutHomeGoal.setOnClickListener {
                    moveToDetail(eatingType, myGoal.id)
                }
            }
        }
    }

    class AddGoalViewHolder(
        private val binding: ItemAddGoalBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        var layout = binding
        fun bind(goalCount: Int, showMakeGoalDialog: () -> Unit) {
            when (goalCount) {
                0 -> binding.layoutGoalInfo.visibility = View.GONE
                1 -> binding.tvAddMoreGoal.setText(R.string.home_two_more_goal)
                2 -> binding.tvAddMoreGoal.setText(R.string.home_one_more_goal)
                3 -> {
                    binding.tvAddMoreGoal.setText(R.string.home_no_more_goal)
                    binding.btnMakeGoal.setVisibility(false)
                }
            }
            if (goalCount < 3) {
                binding.root.setOnClickListener {
                    showMakeGoalDialog()
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (!::inflater.isInitialized)
            inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            HomeGoalViewType.MY_GOAL_TYPE.ordinal -> {
                MyGoalViewHolder(ItemHomeGoalBinding.inflate(inflater, parent, false))
            }
            HomeGoalViewType.ADD_GOAL_TYPE.ordinal -> {
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
                if (currentList[position].isMore) {
                    holder.bind(
                        currentList[position],
                        EatingType.MORE,
                        changeBtnColor,
                        moveToDetail
                    )
                } else {
                    holder.bind(
                        currentList[position],
                        EatingType.LESS,
                        changeBtnColor,
                        moveToDetail
                    )
                }
            }
            // TODO 서버통신 데이터클래스로 변경하면 size 정보 받아온걸로 바꾸기
            is AddGoalViewHolder -> holder.bind(currentList.size - 1, showMakeGoalDialog)
        }
    }

    override fun getItemViewType(position: Int): Int = currentList[position].type.ordinal

    override fun submitList(list: MutableList<HomeGoal>?) {
        if (list != null && list.isNotEmpty()) {
            if (list.last().type == HomeGoalViewType.ADD_GOAL_TYPE) {
                super.submitList(list)
            } else {
                super.submitList(
                    list.plus(
                        mutableListOf(
                            HomeGoal(
                                0,
                                "",
                                false,
                                false,
                                0,
                                HomeGoalViewType.ADD_GOAL_TYPE
                            )
                        )
                    )
                )
            }
        }
    }
}
