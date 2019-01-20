package com.category.api.facade

import com.category.api.model.Products

interface CategoryFacade {

    fun getDiscountedProductForCategory(categoryId: String, labelType: String): Products

}
