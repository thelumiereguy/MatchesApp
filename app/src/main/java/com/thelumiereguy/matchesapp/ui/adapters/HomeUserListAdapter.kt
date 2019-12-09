package com.thelumiereguy.matchesapp.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.thelumiereguy.matchesapp.R
import com.thelumiereguy.matchesapp.databinding.HomeUserPageItemBinding
import com.thelumiereguy.matchesapp.domain.enitity.UsersList
import com.thelumiereguy.matchesapp.ui.adapters.adapterclicklisteners.CustomOnTouchListenerImpl
import com.thelumiereguy.matchesapp.ui.adapters.adapterclicklisteners.ProfileClickListener

class HomeUserListAdapter constructor(
    var usersList: UsersList,
    private var clickListener: ProfileClickListener?
) : RecyclerView.Adapter<HomeUserListAdapter.ViewHolder>() {


    /**
     * Called to append the list with new users
     */
    fun setUser(usersList: UsersList) {
        this.usersList = usersList
        notifyDataSetChanged()
    }


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
        holder.binding.root.setOnTouchListener(CustomOnTouchListenerImpl(-0.05f))
        holder.binding.cardStatus.setStatus(content.status)
        holder.binding.ivUserImage.load(content.picture.large) {
            crossfade(true)
            placeholder(R.drawable.ic_user)
            error(R.drawable.ic_user)
        }
        holder.binding.tvName.text = content.getFullName()
        holder.binding.tvAge.text = content.dob.age.toString()
        holder.binding.tvLocation.text = content.getFullAddress()
    }

    fun onDestroy() {
        clickListener = null
    }

    /**
     * Updates the user properties at the specific position
     *
     * @param position Index of the user
     *
     * @param status The Status to be set - > Accepted or Declined
     */
    fun setStatus(position: Int, status: String) {
        usersList.results[position].status = status
        notifyItemChanged(position)
    }


    class ViewHolder(val binding: HomeUserPageItemBinding) : RecyclerView.ViewHolder(binding.root)


}