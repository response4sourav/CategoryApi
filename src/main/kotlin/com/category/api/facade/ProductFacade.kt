package com.category.api.facade

import com.category.api.model.Product
import com.category.api.model.Products

interface ProductFacade {

    fun populateDiscountedProductsData(products: List<Product>, labelType: String): Products
}
