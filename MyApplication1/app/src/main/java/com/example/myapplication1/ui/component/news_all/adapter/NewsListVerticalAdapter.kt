package com.example.myapplication1.ui.component.news_all.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.example.myapplication1.databinding.ItemNewsDetailBinding
import com.example.myapplication1.model.NewsDtoList
import com.example.myapplication1.ui.utils.FriendsCommon

class NewsListVerticalAdapter(private val context: Context, data: ArrayList<NewsDtoList>) :
    RecyclerView.Adapter<NewsListVerticalAdapter.ViewHolder>() {
    private val data: ArrayList<NewsDtoList>

    interface OnItemClickListener {
        fun onClick(position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener?) {
        mListener = listener
    }

    init {
        this.data = data
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addData(data: ArrayList<NewsDtoList>?) {
        this.data.addAll(data!!)
        notifyDataSetChanged()
    }

    val listData: ArrayList<NewsDtoList>
        get() = data

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemNewsDetailBinding.inflate(
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

    inner class ViewHolder(binding: ItemNewsDetailBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val detailBinding: ItemNewsDetailBinding

        init {
            detailBinding = binding
        }

        fun onBind(context: Context?, position: Int) {
            Glide.with(context!!).load(data[position].imageFullUrl)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .placeholder(FriendsCommon.getShimmer(context))
                .into(detailBinding.newsImg)
            detailBinding.newsImg.setOnClickListener { v ->
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