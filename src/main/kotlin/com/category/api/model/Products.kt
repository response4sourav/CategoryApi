package com.category.api.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude

@JsonIgnoreProperties
@JsonInclude(JsonInclude.Include.NON_NULL)
data class Products (

    var products: List<Product>? = null,
    var errorMessage: String? = null

)
