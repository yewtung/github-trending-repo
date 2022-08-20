package com.example.github_trending_repo.ui.home

import android.view.View
import androidx.core.view.ViewCompat
import com.example.github_trending_repo.R
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.item_list_cell.*


open class BaseListCellItem(
) : Item() {
    override fun getLayout(): Int {
        return R.layout.item_list_cell
    }

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
    }

    protected fun expand(isExpand: Boolean, viewHolder: GroupieViewHolder, isAnimate: Boolean) {
        viewHolder.apply {
            if (isExpand) {
                expand_view.visibility = View.VISIBLE
                divider.visibility = View.GONE
                if (isAnimate) {
                    expand_view.alpha = 0f
                    expand_view.animate().alpha(1f)
                   ViewCompat.setElevation(item_list_cell_layout, 5f)
                }
            } else {
                if (isAnimate) {
                    expand_view.alpha = 1f
                    expand_view.animate().alpha(0f)
                    expand_view.postDelayed({
                        expand_view.visibility = View.GONE
                    }, 250)
                } else {
                    expand_view.visibility = View.GONE
                }
                divider.visibility = View.VISIBLE
                ViewCompat.setElevation(item_list_cell_layout, 0f)
            }
        }
    }
}
