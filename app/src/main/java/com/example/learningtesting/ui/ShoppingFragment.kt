package com.example.learningtesting.ui

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.LEFT
import androidx.recyclerview.widget.ItemTouchHelper.RIGHT
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.learningtesting.R
import com.example.learningtesting.adapter.ShoppingItemAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject

class ShoppingFragment @Inject constructor(
    val shoppingItemAdapter: ShoppingItemAdapter,
    var viewModel : ShoppingViewModel? = null
) : Fragment(R.layout.fragment_shopping) {

    lateinit var fabAddShoppingItem: FloatingActionButton
    lateinit var rvShoppingItems: RecyclerView
    lateinit var tvShoppingItemPrice: TextView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvShoppingItems = view.findViewById(R.id.rvShoppingItems)
        tvShoppingItemPrice = view.findViewById(R.id.tvShoppingItemPrice)
        fabAddShoppingItem = view.findViewById(R.id.fabAddShoppingItem)

        viewModel = viewModel ?: ViewModelProvider(requireActivity()).get(ShoppingViewModel::class.java)

        subscribeToObservers()
        setupRecyclerView()

        fabAddShoppingItem.setOnClickListener {
            findNavController().navigate(
                ShoppingFragmentDirections.actionShoppingFragmentToAddShoppingItemFragment()
            )
        }
    }

    private val itemTouchCallback = object : ItemTouchHelper.SimpleCallback(
        0,
        LEFT or RIGHT

    ) {
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ) = true

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val position = viewHolder.layoutPosition
            val item = shoppingItemAdapter.shoppingItems[position]
            viewModel?.deleteShoppingItem(item)
            Snackbar.make(
                requireView(),
                "Successfully deleted the item",
                Snackbar.LENGTH_LONG
            ).apply {
                setAction("Undo") {
                    viewModel?.insertShoppingItem(item)
                }
                show()
            }
        }
    }

    private fun subscribeToObservers() {
        viewModel?.shoppingItems?.observe(viewLifecycleOwner) {
            shoppingItemAdapter.shoppingItems = it
        }
        viewModel?.totalPrice?.observe(viewLifecycleOwner) {
            val price = it ?: 0f
            val priceText = "Total Price: $price₹"
            tvShoppingItemPrice.text = priceText
        }
    }

    private fun setupRecyclerView() {
        rvShoppingItems.apply {
            adapter = shoppingItemAdapter
            layoutManager = LinearLayoutManager(requireContext())
            ItemTouchHelper(itemTouchCallback).attachToRecyclerView(this)
        }
    }
}