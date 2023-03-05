package org.keepgoeat.presentation.withdraw

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.keepgoeat.R
import org.keepgoeat.databinding.ItemWithdrawBinding
import org.keepgoeat.presentation.model.WithdrawReason
import org.keepgoeat.presentation.type.WithdrawCheckType
import org.keepgoeat.util.ItemDiffCallback

class WithdrawReasonAdapter(context: Context) :
    ListAdapter<WithdrawReason, RecyclerView.ViewHolder>(
        ItemDiffCallback<WithdrawReason>(
            onContentsTheSame = { old, new -> old == new },
            onItemsTheSame = { old, new -> old.reason == new.reason }
        )
    ) {
    private lateinit var inflater: LayoutInflater

    init {
        val reasons = mutableListOf(
            WithdrawReason(context.getString(R.string.withdraw_reason1), WithdrawCheckType.DEFAULT),
            WithdrawReason(context.getString(R.string.withdraw_reason2), WithdrawCheckType.DEFAULT),
            WithdrawReason(context.getString(R.string.withdraw_reason3), WithdrawCheckType.DEFAULT),
            WithdrawReason(context.getString(R.string.withdraw_reason4), WithdrawCheckType.DEFAULT),
        )
        submitList(reasons)
    }

    class WithdrawViewHolder(
        private val binding: ItemWithdrawBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(
            reason: WithdrawReason,
        ) {
            binding.tvWithdrawReason.text = reason.reason
            binding.clickType = reason.isClicked
            binding.layoutWithdrawReason.setOnClickListener {
                binding.clickType = when (binding.clickType) {
                    WithdrawCheckType.CLICKED -> WithdrawCheckType.DEFAULT
                    WithdrawCheckType.DEFAULT -> WithdrawCheckType.CLICKED
                    else -> WithdrawCheckType.DEFAULT
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (!::inflater.isInitialized)
            inflater = LayoutInflater.from(parent.context)
        return WithdrawViewHolder(ItemWithdrawBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is WithdrawViewHolder -> {
                holder.bind(currentList[position])
            }
        }
    }
}
