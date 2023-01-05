package org.keepgoeat.presentation.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.keepgoeat.databinding.ItemGoalStickerBinding
import org.keepgoeat.domain.model.GoalSticker
import org.keepgoeat.presentation.type.EatingType
import org.keepgoeat.util.ItemDiffCallback
import java.time.LocalDate

class GoalStickerListAdapter(private val eatingType: EatingType, private val cellCount: Int) :
    ListAdapter<GoalSticker, GoalStickerListAdapter.FollowerViewHolder>(
        ItemDiffCallback<GoalSticker>(
            onContentsTheSame = { old, new -> old == new },
            onItemsTheSame = { old, new -> old.id == new.id }
        )
    ) {

    /** 전체 셀 개수에서 월별 일 수를 뺀 빈칸 수 */
    private val blankCellCount: Int = cellCount - LocalDate.now().lengthOfMonth()
    private lateinit var inflater: LayoutInflater

    class FollowerViewHolder(private val binding: ItemGoalStickerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: GoalSticker, eatingType: EatingType, isDefault: Boolean) {
            binding.layout.setBackgroundColor(eatingType.cardBackgroundColor)
            if (isDefault)
                binding.ivSticker.setBackgroundResource(eatingType.defaultStickerRes)
            else if (data.isDone)
                binding.ivSticker.setBackgroundResource(eatingType.snailStickerRes)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowerViewHolder {
        if (!::inflater.isInitialized)
            inflater = LayoutInflater.from(parent.context)

        return FollowerViewHolder(ItemGoalStickerBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: FollowerViewHolder, position: Int) {
        holder.onBind(currentList[position], eatingType, position >= itemCount - blankCellCount)
    }
}
