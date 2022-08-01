package com.example.spaceday.superview.view.planet

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.spaceday.R
import com.example.spaceday.supermodel.remote.MarsPhoto

class MarsAdapter : RecyclerView.Adapter<MarsAdapter.ViewHolder>() {

    private lateinit var marsPhoto : ArrayList<MarsPhoto>

    fun setData(photos : ArrayList<MarsPhoto>){
        marsPhoto = photos
        notifyDataSetChanged()
    }

    inner class ViewHolder(view : View) : RecyclerView.ViewHolder(view){
        private val image = itemView.findViewById<ImageView>(R.id.marsImageView)
        private val cameraName = itemView.findViewById<TextView>(R.id.marsCameraNameTextView)
        fun bind(photo : MarsPhoto){
            Log.i("url", "${photo.imageUrl}")
            image.load(photo.imageUrl){
                placeholder(R.drawable.progress_animation)
            }
            cameraName.text = photo.camera
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.item_mars, parent, false) as View)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(marsPhoto[position])
    }

    override fun getItemCount() = marsPhoto.size
}