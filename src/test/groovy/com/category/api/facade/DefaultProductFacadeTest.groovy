package com.category.api.facade

import com.category.api.model.ColorRGB
import com.category.api.model.ColorSwatch
import com.category.api.model.Price
import com.category.api.model.Product
import com.category.api.service.ProductService
import spock.lang.Specification

class DefaultProductFacadeTest extends Specification {
/*
    def productService = GroovyMock ProductService
    def productFacade = new DefaultProductFacade(productService)
    def normalProductPrice = new Price(was: 10f, now: 10f, currency: "GBP")
    def discountedProductPrice = new Price(was: 10f, now: 5f, currency: "GBP")
    def labelType = "XYZ"
    def basicColor = "Blue"

    def "should get discounted products data if available"()  {

        given:
        def normalProduct = new Product(price: normalProductPrice, colorSwatches: Collections.EMPTY_LIST)
        def discountedProduct = new Product(price: discountedProductPrice, colorSwatches: Collections.EMPTY_LIST)

        and:
        productService.getPriceReduction(normalProductPrice) >> 0
        productService.getPriceReduction(discountedProductPrice) >> 1

        expect:
        productFacade.populateDiscountedProductsData([normalProduct, discountedProduct], labelType).products.size() == 1

    }

    def "should get 'Not available' error message if no discounted products available"()  {

        given:
        def normalProduct = new Product(price: normalProductPrice, colorSwatches: Collections.EMPTY_LIST)

        and:
        productService.getPriceReduction(normalProductPrice) >> 0

        expect:
        !productFacade.populateDiscountedProductsData([normalProduct], labelType).errorMessage.isEmpty()

    }

    def "should get colorSwatches inside discounted products data if available"()  {

        given:
        def colorSwatch = new ColorSwatch(basicColor: basicColor)
        def discountedProduct = new Product(price: discountedProductPrice, colorSwatches: Collections.singletonList(colorSwatch))
        def productList = [discountedProduct]

        and:
        productService.getPriceReduction(discountedProductPrice) >> 1
        productService.getHexColor(basicColor) >> ColorRGB.BLUE.colorHex

        expect:
        productFacade.populateDiscountedProductsData(productList, labelType).products.size() == 1
        productFacade.populateDiscountedProductsData(productList, labelType).products[0].colorSwatches.size() == 1
        productFacade.populateDiscountedProductsData(productList, labelType).products[0].colorSwatches[0].rgbColor == ColorRGB.BLUE.colorHex

    }

    def "shouldn't get any colorSwatches inside discounted products data if not available"()  {

        given:
        def discountedProduct = new Product(price: discountedProductPrice, colorSwatches: Collections.EMPTY_LIST)
        def productList = [discountedProduct]

        and:
        productService.getPriceReduction(discountedProductPrice) >> 1

        expect:
        productFacade.populateDiscountedProductsData(productList, labelType).products.size() == 1
        productFacade.populateDiscountedProductsData(productList, labelType).products[0].colorSwatches.size() == 0

    }

    def "should get formatted price inside discounted products data if price available"()  {

        given:
        def discountedProduct = new Product(price: discountedProductPrice, colorSwatches: Collections.EMPTY_LIST)
        def productList = [discountedProduct]

        and:
        productService.getPriceReduction(discountedProductPrice) >> 1
        productService.formattedPrice(discountedProductPrice.now, discountedProductPrice.currency) >> "£5.00"

        expect:
        productFacade.populateDiscountedProductsData(productList, labelType).products.size() == 1
        productFacade.populateDiscountedProductsData(productList, labelType).products[0].nowPrice == "£5.00"

    }
*/
}
