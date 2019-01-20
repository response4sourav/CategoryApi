package com.category.api.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import lombok.Data

@Data
@JsonIgnoreProperties
@JsonInclude(JsonInclude.Include.NON_NULL)
class Products {

    var products: List<Product>? = null
    var errorMessage: String? = null

}
