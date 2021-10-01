package com.example.spaceday.superview.view.month.image

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.spaceday.R
import com.example.spaceday.supermodel.remote.NASAData

class MonthImageAdapter(private val onItemShowVideoBtnClickListener: OnItemShowVideoBtnClickListener)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

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
            imageView.load(imageData.url){
                placeholder(R.drawable.progress_animation)
            }
            imageText.text = imageData.title
        }
    }

    inner class VideoViewHolder(view: View): RecyclerView.ViewHolder(view){
        private val dateBtn: Button = itemView.findViewById(R.id.monthVideoDateButton)
        private val videoView: Button = itemView.findViewById(R.id.monthVideoButton)
        private val videoText: TextView = itemView.findViewById(R.id.monthVideoTextView)

        fun bind(videoData: NASAData){
            dateBtn.text = videoData.date
            videoText.text = videoData.title
            videoView.setOnClickListener{
                onItemShowVideoBtnClickListener.onItemShowVideoBtnClick(videoData)
            }
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
        return when (viewType) {
            TYPE_IMAGE -> { ImageViewHolder(inflater
                .inflate(R.layout.item_month_image, parent, false) as View)
            }
            TYPE_VIDEO -> {
                VideoViewHolder(inflater
                    .inflate(R.layout.item_month_video, parent, false) as View)
            }
            else -> {
                HeaderViewHolder(inflater
                    .inflate(R.layout.item_month_header, parent, false) as View)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int){
        when(getItemViewType(position)) {
            TYPE_IMAGE -> {
                holder as ImageViewHolder
                holder.bind(monthImage[position])
            }
            TYPE_VIDEO -> {
                holder as VideoViewHolder
                holder.bind(monthImage[position])
            }
            else -> {
                holder as HeaderViewHolder
                holder.bind()
            }
        }
    }

    override fun getItemViewType(position: Int): Int{
        return if(monthImage[position].mediaType == "image") TYPE_IMAGE
        else if (monthImage[position].mediaType == "video") TYPE_VIDEO
        else TYPE_HEADER
    }
}