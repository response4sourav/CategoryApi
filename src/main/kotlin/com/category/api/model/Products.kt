package com.category.api.model

data class Products (

    var products: List<Product>? = null,
    var errorMessage: String? = null

)

data class Product (

        var code: String? = null,
        var colorSwatches: List<ColorSwatch>? = null,
        var price: Price? = null,
        var productId: String? = null,
        var title: String? = null,
        var priceLabel: String? = null,
        var nowPrice: String? = null

)

data class ColorSwatch (

        var basicColor: String = "",
        var color: String? = null,
        var colorSwatchUrl: String? = null,
        var imageUrl: String? = null,
        var isAvailable: Boolean? = null,
        var skuId: String? = null,
        var rgbColor: String? = null

)

data class Price (

        var was: Any = 0f,
        var then1: Any = 0f,
        var then2: Any = 0f,
        var now: Any = 0f,
        var uom: String? = null,
        var currency: String = ""
)

data class PriceValue (

        var from: String = "",
        var to: String? = null
)