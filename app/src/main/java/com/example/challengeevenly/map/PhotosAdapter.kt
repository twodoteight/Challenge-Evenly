package com.example.challengeevenly.map

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.challengeevenly.databinding.ItemPhotoBinding
import com.example.challengeevenly.databinding.ItemPlaceBinding
import com.example.challengeevenly.model.*
import com.example.challengeevenly.util.loadCircleCropped
import com.example.challengeevenly.util.loadStandart

class PhotosAdapter() : RecyclerView.Adapter<PhotosAdapter.PhotosItemViewHolder>() {

    var photos = mutableListOf<Photo>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotosItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemPhotoBinding.inflate(inflater, parent, false)
        return PhotosItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PhotosItemViewHolder, position: Int) {
        holder.bind(photos[position])
    }

    override fun getItemCount(): Int {
        return photos.size
    }

    fun updateData(newData: List<Photo>) {
        if (newData.size < 0)
            return
        photos.clear()
        photos.addAll(newData)
        notifyDataSetChanged()
    }

    inner class PhotosItemViewHolder(
        private val binding: ItemPhotoBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(photo: Photo) {
            binding.apply {
                ivPlacePhoto.loadStandart(photo.getLinkWithDimensions(360, 360))
            }
        }
    }
}