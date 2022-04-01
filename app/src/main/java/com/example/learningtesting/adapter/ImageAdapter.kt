package com.example.learningtesting.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.example.learningtesting.R
import java.util.zip.Inflater
import javax.inject.Inject

class ImageAdapter @Inject constructor(
    private val glide: RequestManager
) : RecyclerView.Adapter<ImageAdapter.ImageViewHolder>(){

    class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val ivShoppingImage: ImageView = itemView.findViewById(R.id.ivShoppingImage)
    }

    private val diffCallBack = object : DiffUtil.ItemCallback<String>(){
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this,diffCallBack)

    var images: List<String>
    get() = differ.currentList
    set(value) = differ.submitList(value)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        return ImageViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_image,
                parent,
                false
            )
        )
    }

    private var onItemClickListener : ((String) -> Unit)? = null

    fun setOnItemClickListener(listener : (String)->Unit){
        onItemClickListener = listener
    }

    override fun onBindViewHolder(holderImage: ImageViewHolder, position: Int) {
        val url = images[position]
        glide.load(url).into(holderImage.ivShoppingImage)

        holderImage.itemView.setOnClickListener {
            onItemClickListener?.let {click->
                click(url)
            }
        }
    }

    override fun getItemCount(): Int {
        return images.size
    }
}