package com.example.myapplication1.ui.utils

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration

class HorizontalMarginItemDecoration : ItemDecoration {
    private var context: Context
    private var horizontalMarginInDp: Int
    private var isRight = false

    constructor(context: Context, horizontalMarginInDp: Int) {
        this.context = context
        this.horizontalMarginInDp = horizontalMarginInDp
    }

    constructor(context: Context, horizontalMarginInDp: Int, isRight: Boolean) {
        this.context = context
        this.horizontalMarginInDp = horizontalMarginInDp
        this.isRight = isRight
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        val currentVisibleItemDp = horizontalMarginInDp
        if (isRight) {
            outRect.right = currentVisibleItemDp
        } else {
            outRect.right = currentVisibleItemDp
            outRect.left = currentVisibleItemDp
        }
    }
}