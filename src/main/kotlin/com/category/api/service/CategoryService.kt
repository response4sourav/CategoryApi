package com.category.api.service

import com.category.api.model.Products

interface CategoryService {

    fun getDiscountedProductForCategory(categoryId: String, labelType: String): Products
}
