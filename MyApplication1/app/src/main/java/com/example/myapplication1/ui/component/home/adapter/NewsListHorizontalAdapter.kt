package com.example.myapplication1.ui.component.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.myapplication1.databinding.ItemHomeNewsBinding
import com.example.myapplication1.model.NewsList

class NewsListHorizontalAdapter(private val context: Context, data: ArrayList<NewsList>) :
    RecyclerView.Adapter<NewsListHorizontalAdapter.ViewHolder>() {
    private val data: ArrayList<NewsList>

    interface OnItemClickListener {
        fun onClick(position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener?) {
        mListener = listener
    }

    init {
        this.data = data
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemHomeNewsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(context, position)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class ViewHolder(binding: ItemHomeNewsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val newsBinding: ItemHomeNewsBinding

        init {
            newsBinding = binding
        }

        fun onBind(context: Context?, position: Int) {
            Glide.with(context!!).load(data[position].imageFullUrl)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(newsBinding.newsImg)
            newsBinding.newsImg.setOnClickListener {
                if (mListener != null) {
                    mListener!!.onClick(position)
                }
            }
        }
    }

    companion object {
        private var mListener: OnItemClickListener? = null
    }
}