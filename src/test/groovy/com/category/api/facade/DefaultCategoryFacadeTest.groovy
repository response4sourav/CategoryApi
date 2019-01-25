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

    def "should get products under a category if available"() {

        given:
        def discountedProduct = new Product()
        def allProducts = new Products(products: Collections.singletonList(discountedProduct), errorMessage: "")

        and:
        categoryService.getProductsForCategory(categoryId) >> allProducts
        def discountedProducts = productFacade.populateDiscountedProductsData(allProducts.products, labelType)

        expect:
        discountedProducts == categoryFacade.getDiscountedProductForCategory(categoryId, labelType)

    }

    def "shouldn't get products under a category if connection error"() {

        given:
        def allProducts = new Products(errorMessage: "error")

        and:
        categoryService.getProductsForCategory(categoryId) >> allProducts

        expect:
        !categoryFacade.getDiscountedProductForCategory(categoryId, labelType).errorMessage.isEmpty()

    }

    def "shouldn't get products under a category if no product available"() {

        given:
        def allProducts = new Products(products: Collections.EMPTY_LIST, errorMessage: "")

        and:
        categoryService.getProductsForCategory(categoryId) >> allProducts

        expect:
        !categoryFacade.getDiscountedProductForCategory(categoryId, labelType).errorMessage.isEmpty()

    }
}
