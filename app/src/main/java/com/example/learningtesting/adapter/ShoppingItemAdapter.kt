package com.example.learningtesting.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.example.learningtesting.R
import com.example.learningtesting.data.local.ShoppingItem
import javax.inject.Inject

class ShoppingItemAdapter @Inject constructor(
    private val glide: RequestManager
) : RecyclerView.Adapter<ShoppingItemAdapter.ShoppingItemViewHolder>(){

    class ShoppingItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val tvName: TextView = itemView.findViewById(R.id.tvName)
        val tvShoppingItemAmount : TextView = itemView.findViewById(R.id.tvShoppingItemAmount)
        val tvShoppingItemPrice :TextView = itemView.findViewById(R.id.tvShoppingItemPrice)
        val ivShoppingImage: ImageView = itemView.findViewById(R.id.ivShoppingImage)
    }

    private val diffCallBack = object : DiffUtil.ItemCallback<ShoppingItem>(){
        override fun areItemsTheSame(oldItem: ShoppingItem, newItem: ShoppingItem): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: ShoppingItem, newItem: ShoppingItem): Boolean {
            return oldItem == newItem
        }

    }

    private val differ = AsyncListDiffer(this,diffCallBack)

    var shoppingItems: List<ShoppingItem>
    get() = differ.currentList
    set(value) = differ.submitList(value)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoppingItemViewHolder {
        return ShoppingItemViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_shopping,
                parent,
                false
            )
        )
    }



    override fun onBindViewHolder(holderShoppingItem: ShoppingItemViewHolder, position: Int) {
        val shoppingItem = shoppingItems[position]

        holderShoppingItem.apply {
            glide.load(shoppingItem.imageUrl).into(holderShoppingItem.ivShoppingImage)
            tvName.text = shoppingItem.name
            val amountText = "${shoppingItem.amount}x"
            tvShoppingItemAmount.text = amountText
            val priceText = "${shoppingItem.price}€"
            tvShoppingItemPrice.text = priceText
        }
    }

    override fun getItemCount(): Int {
        return shoppingItems.size
    }
}