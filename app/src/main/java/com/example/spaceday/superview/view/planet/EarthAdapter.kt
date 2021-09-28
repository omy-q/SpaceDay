package com.example.spaceday.superview.view.planet

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.spaceday.R
import com.example.spaceday.supermodel.remote.EarthData

class EarthAdapter: RecyclerView.Adapter<EarthAdapter.ViewHolder>() {

    private lateinit var earthData : ArrayList<EarthData>

    fun setData(data : ArrayList<EarthData>){
        earthData = data
        notifyDataSetChanged()
    }

    inner class ViewHolder(view: View):RecyclerView.ViewHolder(view){
        private val imageView = itemView.findViewById<ImageView>(R.id.earthImageView)
        private val captionTextView = itemView.findViewById<TextView>(R.id.earthCaptionTextView)
        private val dateTextView = itemView.findViewById<TextView>(R.id.earthDateTextView)
        private val timeTextView = itemView.findViewById<TextView>(R.id.earthTimeTextView)

        fun bind(earthDataItem : EarthData){
            imageView.load(earthDataItem.url){
                placeholder(R.drawable.progress_animation)
            }
            captionTextView.text = earthDataItem.caption
            dateTextView.text = earthDataItem.date
            timeTextView.text = earthDataItem.time
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EarthAdapter.ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.item_earth, parent, false) as View)
    }

    override fun onBindViewHolder(holder: EarthAdapter.ViewHolder, position: Int) {
        holder.bind(earthData[position])
    }

    override fun getItemCount() = earthData.size

}