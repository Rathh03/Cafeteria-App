package com.example.orderingapp.Domain

import java.io.Serializable

data class ItemsModel(
    var title: String = "",
    var description: String = "",
    var picUrl: ArrayList<String> = ArrayList(),
    var price: Double = 0.0,
    var rating: Double = 0.0,
    var numberIncart: Int = 0,
    var extra: String = ""
) : Serializable
//data class ItemsModel(
//    val title: String,
//    val description: String,
//    val extra: String,
//    val picUrl: List<String>,
//    val price: Double,
//    val rating: Double
//): Serializable

