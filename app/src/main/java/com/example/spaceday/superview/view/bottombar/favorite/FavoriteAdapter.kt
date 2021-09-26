package com.example.spaceday.superview.view.bottombar.favorite

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.view.menu.MenuView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.spaceday.R
import com.example.spaceday.supermodel.remote.NASAData

class FavoriteAdapter : RecyclerView.Adapter<FavoriteAdapter.ViewHolder>() {

    private lateinit var images : List<NASAData>

    fun setData(data : List<NASAData>){
        images = data
        notifyDataSetChanged()
    }

    inner class ViewHolder(view : View) : RecyclerView.ViewHolder(view){

        private var imageView : ImageView = itemView.findViewById(R.id.image)
        private var title : TextView = itemView.findViewById(R.id.title)
        private var date : TextView = itemView.findViewById(R.id.date)

        fun bind(image : NASAData){
           imageView.load(image.url)
            title.text = image.title
            date.text = image.date
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_favorite_image, parent, false) as View
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(images[position])
    }

    override fun getItemCount() = images.size
}