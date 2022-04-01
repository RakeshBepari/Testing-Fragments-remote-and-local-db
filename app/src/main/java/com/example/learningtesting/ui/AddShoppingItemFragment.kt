package com.example.learningtesting.ui

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.RequestManager
import com.example.learningtesting.R
import com.example.learningtesting.other.Status
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject

class AddShoppingItemFragment @Inject constructor(
    private val glide : RequestManager
): Fragment(R.layout.fragment_add_shopping_item) {

    lateinit var viewModel:ShoppingViewModel
    lateinit var ivShoppingImage: ImageView
    lateinit var btnAddShoppingItem: Button
    private lateinit var etShoppingItemName: EditText
    lateinit var etShoppingItemAmount: EditText
    lateinit var etShoppingItemPrice: EditText

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnAddShoppingItem= view.findViewById(R.id.btnAddShoppingItem)
        ivShoppingImage = view.findViewById(R.id.ivShoppingImage)
        etShoppingItemName = view.findViewById(R.id.etShoppingItemName)
        etShoppingItemAmount = view.findViewById(R.id.etShoppingItemAmount)
        etShoppingItemPrice = view.findViewById(R.id.etShoppingItemPrice)
        viewModel = ViewModelProvider(requireActivity()).get(ShoppingViewModel::class.java)

        subscribeToObservers()

        btnAddShoppingItem.setOnClickListener {
            viewModel.validateAndInsertShoppingItem(
                etShoppingItemName.text.toString(),
                etShoppingItemAmount.text.toString(),
                etShoppingItemPrice.text.toString()
            )
        }

        ivShoppingImage.setOnClickListener {
            findNavController().navigate(
                AddShoppingItemFragmentDirections.actionAddShoppingItemFragmentToImagePickFragment()
            )
        }

        val callback = object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                viewModel.setCurImageUrl("")
                findNavController().popBackStack()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(callback)
    }

    private fun subscribeToObservers(){
        viewModel.curImageUrl.observe(viewLifecycleOwner){
            glide.load(it).into(ivShoppingImage)
        }
        viewModel.insertShoppingItemStatus.observe(viewLifecycleOwner){
            it.getContentIfNotHandled()?.let {result->
                when(result.status){
                    Status.SUCCESS ->{
                        Snackbar.make(
                            requireView(),
                            "The Item was inserted successfully",
                            Snackbar.LENGTH_LONG
                        ).show()
                        findNavController().popBackStack()
                    }
                    Status.ERROR->{
                        Snackbar.make(
                            requireView(),
                            result.message?:"An unknown Error occurred",
                            Snackbar.LENGTH_LONG
                        ).show()
                    }
                    Status.LOADING->{
                        /*NO-OP*/
                    }
                }
            }
        }
    }
}