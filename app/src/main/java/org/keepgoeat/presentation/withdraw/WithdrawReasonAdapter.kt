package org.keepgoeat.presentation.withdraw

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.keepgoeat.databinding.ItemWithdrawBinding
import org.keepgoeat.presentation.model.WithdrawReason
import org.keepgoeat.util.ItemDiffCallback

class WithdrawReasonAdapter(
    private val selectReasons: (WithdrawReason) -> Unit,
) :
    ListAdapter<WithdrawReason, WithdrawReasonAdapter.WithdrawViewHolder>(
        ItemDiffCallback<WithdrawReason>(
            onContentsTheSame = { old, new -> old == new },
            onItemsTheSame = { old, new -> old.reason == new.reason }
        )
    ) {
    private lateinit var inflater: LayoutInflater

    init {
        submitList(WithdrawReason.values().toMutableList())
    }

    class WithdrawViewHolder(
        private val binding: ItemWithdrawBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        var isClicked: Boolean = false
        fun bind(
            reason: WithdrawReason,
            selectReasons: (WithdrawReason) -> Unit,
        ) {
            binding.reason = reason.reason
            binding.layoutWithdrawReason.setOnClickListener {
                isClicked = !isClicked
                binding.isClicked = isClicked
                selectReasons(reason)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WithdrawViewHolder {
        if (!::inflater.isInitialized)
            inflater = LayoutInflater.from(parent.context)
        return WithdrawViewHolder(ItemWithdrawBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: WithdrawViewHolder, position: Int) {
        holder.bind(currentList[position], selectReasons)
    }
}
