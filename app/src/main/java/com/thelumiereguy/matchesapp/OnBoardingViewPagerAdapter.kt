package com.thelumiereguy.matchesapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class OnBoardingViewPagerAdapter constructor(
    private val pageList: List<OnBoardingPageContent>
) : RecyclerView.Adapter<OnBoardingViewPagerAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.onboarding_page_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return pageList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val content : OnBoardingPageContent  = pageList[position]
        holder.description.text = content.description
        holder.image.setImageResource(content.imageId)
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image = itemView.findViewById<ImageView>(R.id.iv_onboarding)
        val description = itemView.findViewById<TextView>(R.id.tv_description)
    }


}