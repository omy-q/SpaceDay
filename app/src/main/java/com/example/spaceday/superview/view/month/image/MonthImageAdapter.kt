package com.example.spaceday.superview.view.month.image

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.spaceday.R
import com.example.spaceday.supermodel.remote.NASAData

class MonthImageAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val TYPE_HEADER = 1
        private const val TYPE_IMAGE = 2
        private const val TYPE_VIDEO = 3
    }

    inner class HeaderViewHolder(view: View): RecyclerView.ViewHolder(view){
        fun bind(){}
    }

    inner class ImageViewHolder(view: View): RecyclerView.ViewHolder(view){
        private val dateBtn: Button = itemView.findViewById(R.id.monthImageDateButton)
        private val imageView: ImageView = itemView.findViewById(R.id.monthImageImageView)
        private val imageText: TextView = itemView.findViewById(R.id.monthImageTextView)

        fun bind(imageData: NASAData){
            dateBtn.text = imageData.date
            imageView.load(imageData.url)
            imageText.text = imageData.explanation
        }
    }

    inner class VideoViewHolder(view: View): RecyclerView.ViewHolder(view){
        private val dateBtn: Button = itemView.findViewById(R.id.monthVideoDateButton)
        private val videoView: Button = itemView.findViewById(R.id.monthVideoButton)
        private val videoText: TextView = itemView.findViewById(R.id.monthVideoTextView)

        fun bind(videoData: NASAData){
            dateBtn.text = videoData.date
            videoText.text = videoData.explanation
        }
    }

    private lateinit var monthImage : ArrayList<NASAData>

    fun setData(data: ArrayList<NASAData>){
        monthImage = data
        notifyDataSetChanged()
    }

    override fun getItemCount() = monthImage.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder{
        val inflater = LayoutInflater.from(parent.context)
        return if (viewType == TYPE_HEADER) { HeaderViewHolder(inflater
            .inflate(R.layout.item_month_header, parent, false) as View)
        } else if (viewType == TYPE_IMAGE){
            ImageViewHolder(inflater
                .inflate(R.layout.item_month_image, parent, false) as View)
        } else{
            VideoViewHolder(inflater
                .inflate(R.layout.item_month_video, parent, false) as View)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int){
        if (getItemViewType(position) == TYPE_HEADER){
            holder as HeaderViewHolder
            holder.bind()
        } else if (getItemViewType(position) == TYPE_IMAGE){
            holder as ImageViewHolder
            holder.bind(monthImage[position])
        } else {
            holder as VideoViewHolder
            holder.bind(monthImage[position])
        }
    }

    override fun getItemViewType(position: Int): Int{
        return if(monthImage[position].mediaType == "image") TYPE_IMAGE
        else if (monthImage[position].mediaType == "video") TYPE_VIDEO
        else TYPE_HEADER
    }
}