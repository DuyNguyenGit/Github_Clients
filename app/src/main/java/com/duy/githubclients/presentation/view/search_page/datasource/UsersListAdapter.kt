package com.duy.githubclients.presentation.view.search_page.datasource

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatTextView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.duy.githubclients.R
import com.duy.githubclients.data.remote.api.models.GithubUserModel

class UsersListAdapter(private val listener: UsersListAdapterInteraction) :
    PagedListAdapter<GithubUserModel, UsersListAdapter.UsersListViewHolder>(usersDiffCallback) {

    lateinit var context: Context

    interface UsersListAdapterInteraction {
        fun onUserItemClick(githubUserModel: GithubUserModel)
    }

    inner class UsersListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val userItem: LinearLayout = itemView.findViewById(R.id.user_item)
        val imgAvatar: ImageView = itemView.findViewById(R.id.img_avatar)
        val txtName: AppCompatTextView = itemView.findViewById(R.id.tv_name)
    }

    override fun onBindViewHolder(holder: UsersListViewHolder, position: Int) {
        val githubUserModel = getItem(position)
        githubUserModel?.let {
            Glide.with(context)
                .load(it.avatarUrl)
                .apply(RequestOptions.circleCropTransform())
                .into(holder.imgAvatar)

            holder.txtName.text = it.login

            holder.userItem.setOnClickListener {
                listener.onUserItemClick(githubUserModel)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersListViewHolder {
        context = parent.context
        val layoutInflater = LayoutInflater.from(context)
        val view = layoutInflater.inflate(R.layout.recycler_view_item, parent, false)
        return UsersListViewHolder(view)
    }

    companion object {
        val usersDiffCallback = object : DiffUtil.ItemCallback<GithubUserModel>() {
            override fun areItemsTheSame(
                oldItem: GithubUserModel,
                newItem: GithubUserModel
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: GithubUserModel,
                newItem: GithubUserModel
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}