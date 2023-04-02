package org.keepgoeat.presentation.my

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.keepgoeat.R
import org.keepgoeat.databinding.LayoutArchivedGoalHeaderBinding
import org.keepgoeat.presentation.type.EatingType

class ArchivedGoalHeaderAdapter(private val eatingTypeClickListener: ((EatingType?) -> Unit)) :
    RecyclerView.Adapter<ArchivedGoalHeaderAdapter.ArchivedGoalHeaderViewHolder>() {
    private lateinit var inflater: LayoutInflater

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ArchivedGoalHeaderViewHolder {
        if (!::inflater.isInitialized)
            inflater = LayoutInflater.from(parent.context)

        return ArchivedGoalHeaderViewHolder(
            LayoutArchivedGoalHeaderBinding.inflate(inflater, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ArchivedGoalHeaderViewHolder, position: Int) {
        holder.onBind(eatingTypeClickListener, ::changeTextAppearance)
    }

    override fun getItemCount() = 1

    private fun changeTextAppearance(
        clickedView: TextView,
        unclickedView1: TextView,
        unclickedView2: TextView,
    ) {
        clickedView.setTextAppearance(R.style.TextAppearance_System5_Bold)
        unclickedView1.setTextAppearance(R.style.TextAppearance_System5)
        unclickedView2.setTextAppearance(R.style.TextAppearance_System5)
        clickedView.setTextColor(clickedView.context.getColor(R.color.gray_800))
        unclickedView1.setTextColor(clickedView.context.getColor(R.color.gray_400))
        unclickedView2.setTextColor(clickedView.context.getColor(R.color.gray_400))
    }

    class ArchivedGoalHeaderViewHolder(
        private val binding: LayoutArchivedGoalHeaderBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(
            eatingTypeClickListener: ((EatingType?) -> Unit),
            changeTextAppearance: (TextView, TextView, TextView) -> Unit,
        ) {
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
