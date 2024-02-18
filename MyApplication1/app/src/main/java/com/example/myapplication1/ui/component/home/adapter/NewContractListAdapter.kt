package com.example.myapplication1.ui.component.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.myapplication1.R
import com.example.myapplication1.databinding.ItemNewContractBinding
import com.example.myapplication1.model.NewAdoptList
import com.example.myapplication1.ui.utils.FriendsCommon


class NewContractListAdapter(private val context: Context, newAdoptList: ArrayList<NewAdoptList>) :
    RecyclerView.Adapter<NewContractListAdapter.ViewHolder>() {
    private val data: ArrayList<NewAdoptList>

    interface OnItemClickListener {
        fun onClick(position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener?) {
        mListener = listener
    }

    init {
        data = newAdoptList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemNewContractBinding.inflate(
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

    inner class ViewHolder(binding: ItemNewContractBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val newContractBinding: ItemNewContractBinding

        init {
            newContractBinding = binding
        }

        fun onBind(context: Context?, position: Int) {
            itemView.setOnClickListener {
                if (mListener != null) {
                    mListener!!.onClick(position)
                }
            }

            //TODO 분양글 이미지
            if (data[position].imageFullUrl.equals("")) {
                Glide.with(context!!)
                    .load(R.drawable.pet_img)
                    .placeholder(FriendsCommon.getShimmer(context))
                    .into(newContractBinding.contentImg)
            } else {
                Glide.with(context!!)
                    .load(data[position].imageFullUrl)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .placeholder(FriendsCommon.getShimmer(context))
                    .into(newContractBinding.contentImg)
            }

            //TODO 분양 형태
            newContractBinding.contractType.text = data[position].adoptTypeLabel

            //TODO 품종
            newContractBinding.breedTxt.text = data[position].breedLabel

            //TODO 성별
            if (data[position].gender.equals("FEMALE")) {
                newContractBinding.genderBadge.setCompoundDrawablesWithIntrinsicBounds(
                    R.drawable.icon_female,
                    0,
                    0,
                    0
                )
                newContractBinding.genderBadge.text = "여아"
            } else {
                newContractBinding.genderBadge.setCompoundDrawablesWithIntrinsicBounds(
                    R.drawable.icon_male,
                    0,
                    0,
                    0
                )
                newContractBinding.genderBadge.text = "남아"
            }

            //TODO 생년월일
            newContractBinding.birthBadge.text = data[position].age

            //TODO 보험 등록 여부
            if (data[position].hasInsurance) {
                newContractBinding.contractBadge.visibility = View.VISIBLE
            } else {
                newContractBinding.contractBadge.visibility = View.GONE
            }
        }
    }

    companion object {
        private var mListener: OnItemClickListener? = null
    }
}