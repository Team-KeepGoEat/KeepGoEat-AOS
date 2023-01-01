package org.keepgoeat.util

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import org.keepgoeat.presentation.type.RecyclerLayoutType
import org.keepgoeat.util.extension.dp

/** @param space : 여백 사이즈
 *  @param gridMatrix : 그리드 레이아웃 메니저 적용 시 row, colume 수를 나타내는 Pair
 *  @param layoutType : 리사이클러뷰 레이아웃 배치 방식 */
class ItemDecorationUtil(
    private val space: Int,
    private val gridMatrix: Pair<Int, Int>? = null,
    private val layoutType: RecyclerLayoutType,
) : RecyclerView.ItemDecoration() {
    private val spaceDp = space.dp
    private val zeroDp = 0.dp

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        val pos = parent.getChildAdapterPosition(view)

        when (layoutType) {
            RecyclerLayoutType.GRID -> {
                if (gridMatrix == null) return

                with(gridMatrix) {
                    outRect.bottom = if (pos / second + 1 < first) spaceDp else zeroDp
                    outRect.right = if (pos % second + 1 < second) spaceDp else zeroDp
                }
            }
            else -> {}
        }
    }
}
