package com.example.myapplication1.ui.utils

import android.content.Context
import androidx.core.content.ContextCompat
import com.example.myapplication1.R
import com.facebook.shimmer.Shimmer.ColorHighlightBuilder
import com.facebook.shimmer.ShimmerDrawable
import kotlin.math.roundToInt

class FriendsCommon {
    companion object {
        fun ConvertDPtoPX(context: Context, dp: Int): Int {
            val density = context.resources.displayMetrics.density
            return (dp.toFloat() * density).roundToInt()
        }

        fun getShimmer(context: Context?): ShimmerDrawable? {
            val shimmer = ColorHighlightBuilder()
                .setBaseColor(ContextCompat.getColor(context!!, R.color.color_shimmer_base))
                .setBaseAlpha(1f)
                .setHighlightColor(ContextCompat.getColor(context, R.color.color_shimmer_highlight))
                .setHighlightAlpha(1f)
                .setDropoff(50f)
                .build()
            val shimmerDrawable = ShimmerDrawable()
            shimmerDrawable.setShimmer(shimmer)
            return shimmerDrawable
        }
    }

}