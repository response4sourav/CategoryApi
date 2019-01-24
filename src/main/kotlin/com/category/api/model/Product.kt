package com.category.api.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude

@JsonIgnoreProperties
@JsonInclude(JsonInclude.Include.NON_NULL)
data class Product (

    var code: String? = null,
    var colorSwatches: List<ColorSwatch>? = null,
    var price: Price? = null,
    var productId: String? = null,
    var title: String? = null,
    var priceLabel: String? = null,
    var nowPrice: String? = null

)
