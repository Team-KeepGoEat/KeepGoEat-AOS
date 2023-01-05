package org.keepgoeat.presentation.onboarding

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.keepgoeat.databinding.ItemOnboardingBinding
import org.keepgoeat.presentation.type.OnBoardingViewType

class OnBoardingAdapter(context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val inflater by lazy { LayoutInflater.from(context) }
    private var onBoardingList = OnBoardingViewType.values()

    init {
        notifyDataSetChanged()
    }

    class OnBoardingViewHolder(private val binding: ItemOnboardingBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(onboarding: OnBoardingViewType) {
            binding.item = onboarding
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnBoardingViewHolder {
        return OnBoardingViewHolder(ItemOnboardingBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is OnBoardingViewHolder -> {
                holder.bind(onBoardingList[position])
            }
        }
    }

    override fun getItemCount(): Int = onBoardingList.size
}
