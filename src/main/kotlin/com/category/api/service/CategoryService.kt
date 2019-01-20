package com.category.api.service

import com.category.api.model.Products

interface CategoryService {

    fun getProductsForCategory(categoryId: String): Products
}
