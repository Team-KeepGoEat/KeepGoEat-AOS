package org.keepgoeat.presentation.my

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.keepgoeat.R
import org.keepgoeat.databinding.LayoutMyHeaderBinding
import org.keepgoeat.presentation.type.EatingType

class MyHeaderAdapter(private val eatingTypeClickListener: ((EatingType?) -> Unit)) :
    RecyclerView.Adapter<MyHeaderAdapter.MyHeaderViewHolder>() {
    private lateinit var inflater: LayoutInflater

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): MyHeaderViewHolder {
        if (!::inflater.isInitialized)
            inflater = LayoutInflater.from(parent.context)

        return MyHeaderViewHolder(LayoutMyHeaderBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: MyHeaderViewHolder, position: Int) {
        holder.onBind(eatingTypeClickListener, ::changeTextAppearance)
    }

    override fun getItemCount() = 1

    private fun changeTextAppearance(clickedView: TextView, unclickedView1: TextView, unclickedView2: TextView) {
        clickedView.setTextAppearance(R.style.TextAppearance_System5_Bold)
        unclickedView1.setTextAppearance(R.style.TextAppearance_System5)
        unclickedView2.setTextAppearance(R.style.TextAppearance_System5)
        clickedView.setTextColor(clickedView.context.getColor(R.color.gray_800))
        unclickedView1.setTextColor(clickedView.context.getColor(R.color.gray_400))
        unclickedView2.setTextColor(clickedView.context.getColor(R.color.gray_400))
    }

    class MyHeaderViewHolder(
        private val binding: LayoutMyHeaderBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(eatingTypeClickListener: ((EatingType?) -> Unit), changeTextAppearance: (TextView, TextView, TextView) -> Unit) {
            with(binding) {
                tvAll.setOnClickListener {
                    eatingTypeClickListener(null)
                    changeTextAppearance(tvAll, tvMore, tvLess)
                }
                tvMore.setOnClickListener {
                    eatingTypeClickListener(EatingType.MORE)
                    changeTextAppearance(tvMore, tvAll, tvLess)
                }
                tvLess.setOnClickListener {
                    eatingTypeClickListener(EatingType.LESS)
                    changeTextAppearance(tvLess, tvAll, tvMore)
                }
            }
        }
    }
}
