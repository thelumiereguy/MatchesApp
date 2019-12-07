package com.thelumiereguy.matchesapp.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import coil.transform.BlurTransformation
import coil.transform.RoundedCornersTransformation
import com.thelumiereguy.matchesapp.R
import com.thelumiereguy.matchesapp.databinding.FavouritesUserRowItemBinding
import com.thelumiereguy.matchesapp.databinding.HomeUserPageItemBinding
import com.thelumiereguy.matchesapp.domain.enitity.UsersList
import com.thelumiereguy.matchesapp.ui.adapters.adapterclicklisteners.ProfileClickListener


class FavouritesUserListAdapter constructor(
    var favouritesUserList: UsersList,
    private var clickListener: ProfileClickListener?
) : RecyclerView.Adapter<FavouritesUserListAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: FavouritesUserRowItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.favourites_user_row_item,
            parent,
            false
        )
        return ViewHolder(
            binding
        )
    }

    override fun getItemCount(): Int {
        return favouritesUserList.results.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val content: UsersList.User = favouritesUserList.results[position]

        holder.binding.root.setOnClickListener {
            clickListener?.onClick(content,holder.binding.ivUserImage)
        }

        holder.binding.ivUserImage.load(content.picture.large) {
            crossfade(true)
            placeholder(R.drawable.ic_heart)
            error(R.drawable.ic_heart)
            transformations(BlurTransformation(holder.binding.root.context, 2f))
            transformations(RoundedCornersTransformation())
        }
        holder.binding.tvName.text = content.name.first + " " + content.name.last
        holder.binding.tvAge.text = content.dob.age.toString()
        holder.binding.tvLocation.text = content.location.city + ", " + content.location.country
    }

    fun onDestroy() {
        clickListener = null
    }


    class ViewHolder(val binding: FavouritesUserRowItemBinding) :
        RecyclerView.ViewHolder(binding.root)


}