package com.thelumiereguy.matchesapp.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import coil.transform.BlurTransformation
import coil.transform.RoundedCornersTransformation
import com.thelumiereguy.matchesapp.R
import com.thelumiereguy.matchesapp.databinding.HomeUserPageItemBinding
import com.thelumiereguy.matchesapp.domain.enitity.UsersList
import com.thelumiereguy.matchesapp.ui.adapters.adapterclicklisteners.ProfileClickListener

class HomeUserListAdapter constructor(
    var usersList: UsersList,
    private var clickListener: ProfileClickListener?
) : RecyclerView.Adapter<HomeUserListAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: HomeUserPageItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.home_user_page_item,
            parent,
            false
        )
        return ViewHolder(
            binding
        )
    }

    override fun getItemCount(): Int {
        return usersList.results.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val content: UsersList.User = usersList.results[position]


        holder.binding.root.setOnClickListener {
            clickListener?.onClick(content, holder.binding.ivUserImage)
        }


        holder.binding.ivUserImage.load(content.picture.large) {
            crossfade(true)
            placeholder(R.drawable.ic_heart)
            error(R.drawable.ic_heart)
        }
        holder.binding.tvName.text = content.name.first + " " + content.name.last
        holder.binding.tvAge.text = content.dob.age.toString()
        holder.binding.tvLocation.text = content.location.city + ", " + content.location.country
    }

    fun onDestroy() {
        clickListener = null
    }


    class ViewHolder(val binding: HomeUserPageItemBinding) : RecyclerView.ViewHolder(binding.root)


}