package com.example.waterjarmanagement.customer

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.waterjarmanagement.R
import com.example.waterjarmanagement.customer.view_models.OrderViewModel
import com.example.waterjarmanagement.databinding.ActivityOrderBinding

class OrderActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOrderBinding
    private lateinit var orderViewModel: OrderViewModel
    private lateinit var sellerId: String
    private var jarPrice: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sellerId = intent.getStringExtra("sellerId").toString()
        jarPrice = intent.getIntExtra("jarPrice", 0)

        orderViewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(application))[OrderViewModel::class.java]

        var checkedRadioButtonId = binding.radioGroup.checkedRadioButtonId // Returns View.NO_ID if nothing is checked.

        binding.radioGroup.setOnCheckedChangeListener { group, checkedId ->
            // Responds to child RadioButton checked/unchecked
            checkedRadioButtonId = checkedId
            if(checkedRadioButtonId == R.id.monthlyOrder){
                binding.jarQuantity.text = (30).toString()
                binding.amountToPay.text = (30 * jarPrice).toString()
                binding.plusButton.isClickable = false
                binding.minusButton.isClickable = false
            }
            else{
                binding.jarQuantity.text = orderViewModel.quantity.toString()
                binding.amountToPay.text = (orderViewModel.quantity * jarPrice).toString()
                binding.plusButton.isClickable = true
                binding.minusButton.isClickable = true
            }
        }

        binding.plusButton.setOnClickListener {
            orderViewModel.quantity++
            binding.jarQuantity.text = orderViewModel.quantity.toString()
            binding.jarQuantity.text = orderViewModel.quantity.toString()
            binding.amountToPay.text = (jarPrice * orderViewModel.quantity).toString()
        }

        binding.minusButton.setOnClickListener {
            if(orderViewModel.quantity > 0){
                orderViewModel.quantity--
            }
            binding.jarQuantity.text = orderViewModel.quantity.toString()
            binding.amountToPay.text = (jarPrice * orderViewModel.quantity).toString()
        }


        orderViewModel.getCustomer()
        orderViewModel.getSeller(sellerId)

        orderViewModel.customer.observe(this, Observer {
            setCustomerUI()
        })

        orderViewModel.seller.observe(this, Observer {
            setSellerUI()
        })

        binding.confirmOrder.setOnClickListener {
            if(checkedRadioButtonId == R.id.monthlyOrder){
                orderViewModel.orderMonthlyOrder()
            }
            else{
                orderViewModel.orderPayGoOrder()
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setCustomerUI() {
        binding.customerName.text = orderViewModel.customer.value?.getName()
        binding.address.text = orderViewModel.customer.value?.getAddress()
        binding.jarQuantity.text = orderViewModel.quantity.toString()
        binding.amountToPay.text = (jarPrice * orderViewModel.quantity).toString()
    }

    @SuppressLint("SetTextI18n")
    private fun setSellerUI() {
        binding.ownerName.text = orderViewModel.seller.value?.getOwnerName()
        binding.companyName.text = orderViewModel.seller.value?.getCompanyName()
        binding.contactNumber.text = "+91 " + orderViewModel.seller.value?.getCustomerCareNumber()
        binding.priceForOneJar.text = orderViewModel.seller.value?.getJarPrice().toString()
    }
}