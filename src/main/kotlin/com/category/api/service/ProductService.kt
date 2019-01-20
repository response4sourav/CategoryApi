package com.category.api.service

import com.category.api.model.Price

interface ProductService {

    fun getHexColor(basicColor: String): String

    fun formattedPrice(price: Any, currency: String): String

    fun formatPriceLabel(price: Price, labelType: String): String

    fun getPriceReduction(price: Price): Double
}
