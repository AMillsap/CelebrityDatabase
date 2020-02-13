package com.example.celebritydatabase

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.celebrity_item.view.*

class CelebrityRVAdapter(var celebrityList : ArrayList<Celebrity>, val callback: CelebrityCallback)
    : RecyclerView.Adapter<CelebrityRVAdapter.ViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.celebrity_item, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.populatePersonItem(celebrityList[position])

    override fun getItemCount() = celebrityList.size

    fun updateList(passedList : ArrayList<Celebrity>) {
        celebrityList = passedList
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        fun populatePersonItem(celebrity: Celebrity) {
            itemView.tvFirstName.text = celebrity.firstName
            itemView.tvLastName.text = celebrity.lastName
            itemView.setOnClickListener{ callback.passCelebrity(celebrity)}
        }
    }

}