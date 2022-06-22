package com.example.challengeevenly.places

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.challengeevenly.databinding.ItemPlaceBinding
import com.example.challengeevenly.model.Place
import com.example.challengeevenly.model.getCategoryIconURL
import com.example.challengeevenly.util.loadCircleCropped

class PlacesListAdapter(
    private val fragment: Fragment,
    private val onPlaceClicked: (Place) -> Unit
) : RecyclerView.Adapter<PlacesListAdapter.PlacesItemViewHolder>() {

    var places = mutableListOf<Place>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlacesItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemPlaceBinding.inflate(inflater, parent, false)
        return PlacesItemViewHolder(binding, onPlaceClicked)
    }

    override fun onBindViewHolder(holder: PlacesItemViewHolder, position: Int) {
        holder.bind(places[position])
    }

    override fun getItemCount(): Int {
        return places.size
    }

    fun updateData(newData: List<Place>) {
        if (newData.size < 0)
            return
        places.clear()
        places.addAll(newData)
        notifyDataSetChanged()
    }

    inner class PlacesItemViewHolder(
        private val binding: ItemPlaceBinding,
        private val onPlaceClicked: (Place) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(place: Place) {
            binding.apply {
                tvPlaceTitle.text = place.name
                tvPlaceCategories.text = place.categories.joinToString(" | ") { it.name }
                ivIcon.loadCircleCropped(place.getCategoryIconURL())
                root.setOnClickListener { onPlaceClicked(place) }
            }
        }
    }
}