package com.category.api.facade

import com.category.api.model.Product
import com.category.api.model.Products
import com.category.api.service.CategoryService
import spock.lang.Specification

class DefaultCategoryFacadeTest extends Specification {

    def productFacade = GroovyMock ProductFacade
    def categoryService = GroovyMock CategoryService
    def categoryFacade = new DefaultCategoryFacade(categoryService, productFacade)
    def categoryId = "12345"
    def labelType = "XYZ"

    def "should get discounted products under a category if available"() {

        given:
        def allProducts = new Products()
        def discountedProduct = new Product()
        allProducts.setProducts(Collections.singletonList(discountedProduct))
        allProducts.errorMessage = ""

        and:
        categoryService.getProductsForCategory(categoryId) >> allProducts
        def discountedProducts = productFacade.populateDiscountedProductsData(allProducts, labelType)

        expect:
        discountedProducts == categoryFacade.getDiscountedProductForCategory(categoryId, labelType)

    }

    def "shouldn't get discounted products under a category if error"() {

        given:
        def allProducts = new Products()
        allProducts.errorMessage = "error"

        and:
        categoryService.getProductsForCategory(categoryId) >> allProducts

        expect:
        !categoryFacade.getDiscountedProductForCategory(categoryId, labelType).errorMessage.isEmpty()

    }

    def "shouldn't get discounted products under a category if no product available"() {

        given:
        def allProducts = new Products()
        allProducts.setProducts(Collections.EMPTY_LIST)
        allProducts.errorMessage = ""

        and:
        categoryService.getProductsForCategory(categoryId) >> allProducts

        expect:
        !categoryFacade.getDiscountedProductForCategory(categoryId, labelType).errorMessage.isEmpty()

    }
}
