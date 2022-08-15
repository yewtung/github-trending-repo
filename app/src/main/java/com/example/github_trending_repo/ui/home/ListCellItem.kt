package com.example.github_trending_repo.ui.home

import com.bumptech.glide.Glide
import com.example.github_trending_repo.R
import com.example.github_trending_repo.api.entity.TrendingRepository
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import kotlinx.android.synthetic.main.item_list_cell.*


open class ListCellItem(
    var repo: TrendingRepository
) : BaseListCellItem() {
    override fun getLayout(): Int {
        return R.layout.item_list_cell
    }

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.apply {
            repo_author.text = repo.author
            repo_name.text = repo.name
            Glide.with(viewHolder.root.context)
                .load(repo.image)
                .into(repo_image)
            repo_stars.text = repo.stars.toString()
            repo_fork.text = repo.forks.toString()
            repo_language.text = repo.language

            top_view.setOnClickListener{
                repo.isExpand = !repo.isExpand
                expand(repo.isExpand, this, true)
            }
        }
    }

}
