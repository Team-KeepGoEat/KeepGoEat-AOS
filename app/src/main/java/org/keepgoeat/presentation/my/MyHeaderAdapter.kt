package org.keepgoeat.presentation.my

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
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
        holder.onBind(eatingTypeClickListener)
    }

    override fun getItemCount() = 1

    class MyHeaderViewHolder(
        private val binding: LayoutMyHeaderBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(eatingTypeClickListener: ((EatingType?) -> Unit)) {
            with(binding) {
                tvAll.setOnClickListener {
                    eatingTypeClickListener(null)
                }
                tvMore.setOnClickListener {
                    eatingTypeClickListener(EatingType.MORE)
                }
                tvLess.setOnClickListener {
                    eatingTypeClickListener(EatingType.LESS)
                }
            }
        }
    }
}
