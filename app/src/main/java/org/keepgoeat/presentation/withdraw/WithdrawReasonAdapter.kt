package org.keepgoeat.presentation.withdraw

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.keepgoeat.databinding.ItemWithdrawBinding
import org.keepgoeat.presentation.model.WithdrawReason
import org.keepgoeat.util.ItemDiffCallback

class WithdrawReasonAdapter :
    ListAdapter<WithdrawReason, WithdrawReasonAdapter.WithdrawViewHolder>(
        ItemDiffCallback<WithdrawReason>(
            onContentsTheSame = { old, new -> old == new },
            onItemsTheSame = { old, new -> old.reason == new.reason }
        )
    ) {
    private lateinit var inflater: LayoutInflater

    init {
        val reasons = mutableListOf(
            WithdrawReason.REASON1,
            WithdrawReason.REASON2,
            WithdrawReason.REASON3,
            WithdrawReason.REASON4,
        )
        submitList(reasons)
    }

    class WithdrawViewHolder(
        private val binding: ItemWithdrawBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        var isClicked: Boolean = false
        fun bind(
            reason: WithdrawReason,
        ) {
            binding.reason = reason.reason
            binding.isClicked = isClicked
            binding.layoutWithdrawReason.setOnClickListener {
                isClicked = !isClicked
                binding.isClicked = isClicked
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WithdrawViewHolder {
        if (!::inflater.isInitialized)
            inflater = LayoutInflater.from(parent.context)
        return WithdrawViewHolder(ItemWithdrawBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: WithdrawViewHolder, position: Int) {
        holder.bind(currentList[position])
    }
}
