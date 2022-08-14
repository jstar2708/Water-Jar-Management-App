package com.example.waterjarmanagement.customer.models

class PayGo {

    private var quantity: Int = 0
    private lateinit var sellerId: String
    private var isMonthlyJarDelivered: Boolean = false
    private var jarPrice: Int = 0
    private lateinit var sellerName: String

    constructor(){}
    constructor(quantity: Int, sellerId: String, isMonthlyJarDelivered: Boolean, jarPrice: Int, sellerName: String) {
        this.quantity = quantity
        this.sellerId = sellerId
        this.isMonthlyJarDelivered = isMonthlyJarDelivered
        this.jarPrice = jarPrice
        this.sellerName = sellerName
    }

    fun getSellerName(): String{
        return this.sellerName
    }

    fun setSellerName(value: String){
        this.sellerName = value
    }

    fun getJarPrice(): Int{
        return this.jarPrice
    }

    fun setJarPrice(value: Int){
        this.jarPrice = value
    }

    fun getQuantity(): Int{
        return this.quantity
    }

    fun setQuantity(value: Int){
        this.quantity = value
    }

    fun getSellerId(): String{
        return this.sellerId
    }
    fun setSellerId(value: String){
        this.sellerId = value
    }

    fun getIsMonthlyJarDelievered(): Boolean{
        return this.isMonthlyJarDelivered
    }

    fun setIsMonthlyJarDelievered(value: Boolean){
        this.isMonthlyJarDelivered = value
    }
}