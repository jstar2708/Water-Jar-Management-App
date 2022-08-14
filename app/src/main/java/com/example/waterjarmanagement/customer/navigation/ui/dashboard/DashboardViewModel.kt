package com.example.waterjarmanagement.customer.navigation.ui.dashboard

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.waterjarmanagement.customer.models.Jars
import com.example.waterjarmanagement.customer.models.Customer
import com.example.waterjarmanagement.customer.models.PayGo
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class DashboardViewModel : ViewModel() {
    var orderList: MutableLiveData<ArrayList<Jars>> = MutableLiveData(ArrayList())
    var payGoOrderList: MutableLiveData<ArrayList<PayGo>> = MutableLiveData(ArrayList())
    private val database: FirebaseDatabase = FirebaseDatabase.getInstance("https://waterjarmanagement-default-rtdb.asia-southeast1.firebasedatabase.app/")
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private var customer: MutableLiveData<Customer> = MutableLiveData()

    fun getOrdersList(){
        database.reference.child("Customer").child(auth.currentUser?.uid.toString()).addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                customer.value = snapshot.getValue(Customer::class.java)!!
                orderList.value = customer.value?.sellers
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("ERROR", error.message)
                Log.e("ERROR", error.message)
            }

        })
    }

    fun getPayGoOrdersList(){
        database.reference.child("Customer").child(auth.currentUser?.uid.toString()).addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                customer.value = snapshot.getValue(Customer::class.java)!!
                payGoOrderList.value = customer.value?.payGoOrders
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("ERROR", error.message)
                Log.e("ERROR", error.message)
            }

        })
    }

    fun updateMonthlyOrder(){
        customer.value?.sellers = orderList.value!!
        database.reference.child("Customer").child(customer.value?.getUserId().toString()).setValue(customer.value)
    }

    fun updatePayGoOrder(){
        customer.value?.payGoOrders = payGoOrderList.value!!
        database.reference.child("Customer").child(customer.value?.getUserId().toString()).setValue(customer.value)
    }


}