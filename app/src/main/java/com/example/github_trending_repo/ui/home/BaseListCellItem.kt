package com.example.github_trending_repo.ui.home

import android.view.View
import com.bumptech.glide.Glide
import com.example.github_trending_repo.R
import com.example.github_trending_repo.api.entity.TrendingRepository
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
                if (isAnimate) {
                    expand_view.alpha = 0f
                    expand_view.animate().alpha(1f)
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
            }
        }
    }
}
