package com.example.simpletodo

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

//bridge that tells the recycler view how to display data
class TaskItemAdapter(val listOfItems: List<String>, val longClickListener: OnLongClickListener, val clickListener: onClickListener) : RecyclerView.Adapter<TaskItemAdapter.ViewHolder>(){

    //the list is initialized in main so we use an interface to change it from the adapter
    interface OnLongClickListener {
        fun onItemLongClicked(position: Int)
    }
    interface onClickListener {
        fun onItemClicked(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        //Inflate the custom layout
        val contactView = inflater.inflate(android.R.layout.simple_list_item_1, parent, false)
        // return a value
        return ViewHolder(contactView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //get data model
        val item = listOfItems.get(position)
        holder.textView.text = item
    }

    override fun getItemCount(): Int {
        return listOfItems.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        // Store references to elements in view
        val textView: TextView
        init {
            textView = itemView.findViewById(android.R.id.text1)
            //can long click to remove item
            itemView.setOnLongClickListener {
                //Log.i("Pepper", "Long click " + adapterPosition)
                longClickListener.onItemLongClicked(adapterPosition)
                true
            }
            //can click to edit item
            itemView.setOnClickListener {
                clickListener.onItemClicked(adapterPosition)
                true
            }
        }
    }
}