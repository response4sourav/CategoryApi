package com.category.api.controller

import com.category.api.facade.CategoryFacade
import com.category.api.model.Products
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody

@Controller
class CategoryApiController @Autowired
constructor(private val categoryFacade: CategoryFacade) {

    @RequestMapping("/**/categories/{categoryId}/discounted-products")
    @ResponseBody
    fun getDiscountedProducts(@PathVariable categoryId: String,
                              @RequestParam(name = "labelType", required = false, defaultValue = "showWasNow") labelType: String): Products {

        return categoryFacade.getDiscountedProductForCategory(categoryId, labelType)

    }
}
