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
    : RecyclerView.Adapter<MonthImageAdapter.BaseViewHolder>() {

    companion object {
        private const val TYPE_HEADER = 1
        private const val TYPE_IMAGE = 2
        private const val TYPE_VIDEO = 3
    }

    fun appendItem(data: NASAData){
        monthImage.add(data)
        notifyItemInserted(itemCount - 1)
    }

    abstract inner class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        abstract fun bind(data: NASAData)
        fun removeItem(){
            monthImage.removeAt(layoutPosition)
            notifyItemRemoved(layoutPosition)
        }
        fun moveUp(){
            layoutPosition.takeIf { it > 1 }?.also { currentPosition ->
                monthImage.removeAt(currentPosition).apply {
                    monthImage.add(currentPosition - 1, this)
                }
                notifyItemMoved(currentPosition, currentPosition - 1)
            }
        }

        fun moveDown(){
            layoutPosition.takeIf { it < monthImage.size - 1 }?.also { currentPosition ->
                monthImage.removeAt(currentPosition).apply {
                    monthImage.add(currentPosition + 1, this)
                }
                notifyItemMoved(currentPosition, currentPosition + 1)
            }
        }
    }

    inner class HeaderViewHolder(view: View): BaseViewHolder(view) {
        override fun bind(headerData: NASAData) {
        }
    }

    inner class ImageViewHolder(view: View): BaseViewHolder(view){
        private val dateBtn: Button = itemView.findViewById(R.id.monthImageDateButton)
        private val imageView: ImageView = itemView.findViewById(R.id.monthImageImageView)
        private val imageText: TextView = itemView.findViewById(R.id.monthImageTextView)
        private val removeItemView : ImageView = itemView.findViewById(R.id.monthImageDelete)
        private val upItemView : ImageView = itemView.findViewById(R.id.monthImageUp)
        private val downItemView : ImageView = itemView.findViewById(R.id.monthImageDown)

        override fun bind(imageData: NASAData){
            dateBtn.text = imageData.date
            imageView.load(imageData.url){
                placeholder(R.drawable.progress_animation)
            }
            imageText.text = imageData.title
            removeItemView.setOnClickListener {removeItem()}
            upItemView.setOnClickListener { moveUp() }
            downItemView.setOnClickListener { moveDown() }
        }
    }

    inner class VideoViewHolder(view: View): BaseViewHolder(view){
        private val dateBtn: Button = itemView.findViewById(R.id.monthVideoDateButton)
        private val videoView: Button = itemView.findViewById(R.id.monthVideoButton)
        private val videoText: TextView = itemView.findViewById(R.id.monthVideoTextView)
        private val removeItemView : ImageView = itemView.findViewById(R.id.monthVideoDelete)
        private val upItemView : ImageView = itemView.findViewById(R.id.monthVideoUp)
        private val downItemView : ImageView = itemView.findViewById(R.id.monthVideoDown)

        override fun bind(videoData: NASAData){
            dateBtn.text = videoData.date
            videoText.text = videoData.title
            videoView.setOnClickListener{
                onItemShowVideoBtnClickListener.onItemShowVideoBtnClick(videoData)
            }
            removeItemView.setOnClickListener {removeItem()}
            upItemView.setOnClickListener { moveUp() }
            downItemView.setOnClickListener { moveDown() }
        }
    }

    private lateinit var monthImage : ArrayList<NASAData>

    fun setData(data: ArrayList<NASAData>){
        monthImage = data
        notifyDataSetChanged()
    }

    override fun getItemCount() = monthImage.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder{
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

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.bind(monthImage[position])
    }

    override fun getItemViewType(position: Int): Int{
        return if(monthImage[position].mediaType == "image") TYPE_IMAGE
        else if (monthImage[position].mediaType == "video") TYPE_VIDEO
        else TYPE_HEADER
    }
}