package com.e.occanotestsidep.ui.main.log

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintSet.WRAP_CONTENT
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import com.e.occanosidetest.models.Log
import com.e.occanotestsidep.R
import kotlinx.android.synthetic.main.log_rv_item.view.*

class LogAdapter(private val interaction: Interaction? = null) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Log>() {

        override fun areItemsTheSame(oldItem: Log, newItem: Log): Boolean {
           return oldItem.content == newItem.content
        }

        override fun areContentsTheSame(oldItem: Log, newItem: Log): Boolean {
            return oldItem == newItem
        }

    }
    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return LogViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.log_rv_item,
                parent,
                false
            ),
            interaction
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is LogViewHolder -> {
                holder.bind(differ.currentList.get(position))
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submitList(list: List<Log>) {
        differ.submitList(list)
    }

    class LogViewHolder
    constructor(
        itemView: View,
        private val interaction: Interaction?
    ) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: Log) = with(itemView) {
            itemView.setOnClickListener {
                interaction?.onItemSelected(adapterPosition, item)
            }
            itemView.tv_log_rv_item.text = item.content
        }
    }

    interface Interaction {
        fun onItemSelected(position: Int, item: Log)
    }

}
