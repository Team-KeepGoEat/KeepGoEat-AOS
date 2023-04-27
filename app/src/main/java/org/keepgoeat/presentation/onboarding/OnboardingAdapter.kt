package org.keepgoeat.presentation.onboarding

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.keepgoeat.databinding.ItemOnboardingBinding
import org.keepgoeat.presentation.type.OnBoardingViewType

class OnboardingAdapter(context: Context) :
    RecyclerView.Adapter<OnboardingAdapter.OnBoardingViewHolder>() {
    private val inflater by lazy { LayoutInflater.from(context) }
    private val onBoardingList = OnBoardingViewType.values()

    init {
        notifyDataSetChanged()
    }

    class OnBoardingViewHolder(private val binding: ItemOnboardingBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(onboarding: OnBoardingViewType) {
            binding.item = onboarding
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnBoardingViewHolder =
        OnBoardingViewHolder(ItemOnboardingBinding.inflate(inflater, parent, false))

    override fun onBindViewHolder(holder: OnBoardingViewHolder, position: Int) {
        holder.bind(onBoardingList[position])
    }

    override fun getItemCount(): Int = onBoardingList.size
}
