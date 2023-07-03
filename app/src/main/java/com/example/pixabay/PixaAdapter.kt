package com.example.pixabay

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load
import com.example.pixabay.databinding.ItemImageBinding

class PixaAdapter : Adapter<PixaAdapter.PixaViewHolder>() {
    var arrayList = arrayListOf<ImageModel>()

    class PixaViewHolder(var binding: ItemImageBinding) : ViewHolder(binding.root){
        fun onBind (model : ImageModel){
            binding.imageView.load(model.largeImageURL)
        }
    }

    fun addImages(list : ArrayList<ImageModel>) {
        arrayList.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PixaViewHolder {
        return PixaViewHolder(
            ItemImageBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return arrayList.size

    }

    override fun onBindViewHolder(holder: PixaViewHolder, position: Int) {
       holder.onBind(arrayList[position])
    }


}
