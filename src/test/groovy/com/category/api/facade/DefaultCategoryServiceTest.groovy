package com.category.api.facade

import com.category.api.client.CategoryApiClient
import com.category.api.model.Price
import com.category.api.model.Product
import com.category.api.model.Products
import com.category.api.service.DefaultCategoryService
import com.category.api.service.ProductService
import spock.lang.Specification

class DefaultCategoryServiceTest extends Specification {

    /*def productService = GroovyMock ProductService
    def categoryApiClient = GroovyMock CategoryApiClient
    def categoryService = new DefaultCategoryService(categoryApiClient, productService)
    def normalProductPrice = new Price(was: 10f, now: 10f, currency: "GBP")
    def discountedProductPrice = new Price(was: 10f, now: 5f, currency: "GBP")
    def labelType = "XYZ"
    def categoryId = "123"

    def "should get products under a category if available"() {

        given:
        def normalProduct = new Product(price: normalProductPrice, colorSwatches: Collections.EMPTY_LIST)
        def discountedProduct = new Product(price: discountedProductPrice, colorSwatches: Collections.EMPTY_LIST)
        def productList = [normalProduct, discountedProduct]

        and:
        categoryApiClient.getProductsInCategory(categoryId) >> productList
        def discountedProducts = categoryService.populateDiscountedProductsData(productList.products, labelType)

        expect:
        discountedProducts == categoryService.getDiscountedProductForCategory(categoryId, labelType)

    }

    def "shouldn't get products under a category if connection error"() {

        given:
        def allProducts = new Products(errorMessage: "error")

        and:
        categoryApiClient.getProductsInCategory(categoryId) >> allProducts

        expect:
        null == categoryService.getDiscountedProductForCategory(categoryId, labelType)

    }
*/
}
