package com.example.myapplication1.ui.component.home.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.myapplication1.databinding.ItemHomeBannerBinding
import com.example.myapplication1.model.BannerList

class SlideBannerAdapter(
    private val context: Context,
    viewPager: ViewPager2,
    sliderItems: ArrayList<BannerList>
) :
    RecyclerView.Adapter<SlideBannerAdapter.SlideBannerHolder>() {
    private val sliderItems: ArrayList<BannerList>
    private val viewPager2: ViewPager2

    interface OnItemClickListener {
        fun onClick(position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener?) {
        mListener = listener
    }

    init {
        this.sliderItems = sliderItems
        viewPager2 = viewPager
    }

    val sliderItemsSize: Int
        get() = sliderItems.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SlideBannerHolder {
        return SlideBannerHolder(
            ItemHomeBannerBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: SlideBannerHolder, position: Int) {
        holder.onBind(context, position)
        if (position == sliderItems.size - 2) {
            viewPager2.post(holder.runnable)
        }
    }

    override fun getItemCount(): Int {
        return sliderItems.size
    }

    inner class SlideBannerHolder(binding: ItemHomeBannerBinding) :
        RecyclerView.ViewHolder(binding.getRoot()) {
        private val bannerBinding: ItemHomeBannerBinding
        fun onBind(context: Context?, position: Int) {
            Glide.with(context!!).load(sliderItems[position].imageFullUrl)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .centerCrop()
                .into(bannerBinding.bannerImg)
            if (sliderItems[position].linkFullUrl == null || sliderItems[position].linkFullUrl.equals(
                    ""
                )
            ) {
                //TODO do nothing
            } else {
                bannerBinding.bannerImg.setOnClickListener { v ->
                    if (mListener != null) {
                        mListener!!.onClick(position)
                    }
                }
            }
        }

        @SuppressLint("NotifyDataSetChanged")
        val runnable = Runnable {
            sliderItems.addAll(sliderItems)
            notifyDataSetChanged()
        }

        init {
            bannerBinding = binding
        }
    }

    companion object {
        private var mListener: OnItemClickListener? = null
    }
}