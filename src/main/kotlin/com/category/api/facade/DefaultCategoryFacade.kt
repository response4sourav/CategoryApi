package com.category.api.facade

import com.category.api.model.Products
import com.category.api.service.CategoryService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.util.CollectionUtils

@Component
class DefaultCategoryFacade @Autowired
constructor(private val categoryService: CategoryService, private val productFacade: ProductFacade) : CategoryFacade {

    override fun getDiscountedProductForCategory(categoryId: String, labelType: String): Products {

        val products = categoryService.getProductsForCategory(categoryId)
        if (!products.errorMessage.isNullOrEmpty()) return products

        if (CollectionUtils.isEmpty(products.products)) {
            products.errorMessage = "Didn't found any product under category $categoryId"
            return products
        }

        return productFacade.populateDiscountedProductsData(products.products!!, labelType)
    }


}
