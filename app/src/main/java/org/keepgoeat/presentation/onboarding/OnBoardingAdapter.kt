package org.keepgoeat.presentation.onboarding

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.keepgoeat.databinding.ItemOnboardingBinding

class OnBoardingAdapter(context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val inflater by lazy { LayoutInflater.from(context) }
    private var onBoardingList: List<OnBoardingItem> = emptyList()

    class OnBoardingViewHolder(private val binding: ItemOnboardingBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(onboarding: OnBoardingItem) {
            binding.tvOnboardingTitle.setText(onboarding.title)
            binding.tvOnboardingDes.setText(onboarding.des)
            binding.ivOnboarding.setImageDrawable(binding.root.context.getDrawable(onboarding.image))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemOnboardingBinding.inflate(inflater, parent, false)
        return OnBoardingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentItem = onBoardingList[position]
        (holder as OnBoardingViewHolder).bind(currentItem)
    }

    override fun getItemCount(): Int = onBoardingList.size

    fun setOnBoardingList(onboardingList: List<OnBoardingItem>) {
        this.onBoardingList = onboardingList.toList()
        notifyDataSetChanged()
    }
}
