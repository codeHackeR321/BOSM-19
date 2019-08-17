package com.dvm.appd.bosm.dbg.more

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dvm.appd.bosm.dbg.R
import kotlinx.android.synthetic.main.card_recycler_more.view.*

class MoreAdapter(private val listener: onMoreItemClicked) : RecyclerView.Adapter<MoreAdapter.moreViewHolder>() {

    var moreItems : List<String> = emptyList()

    interface onMoreItemClicked {
        fun moreButtonClicked(item: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): moreViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_recycler_more,parent,false)
        return moreViewHolder(view)
    }

    override fun getItemCount(): Int {
        return moreItems.size
    }

    override fun onBindViewHolder(holder: moreViewHolder, position: Int) {
        holder.title.text = moreItems[position]
        holder.parent.setOnClickListener {
            listener.moreButtonClicked(position)
        }
    }

    inner class moreViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        var title = view.text_card_more_title
        var thimbnail = view.img_card_more_thumbnail
        var parent = view.linear_card_more_parent
    }
}