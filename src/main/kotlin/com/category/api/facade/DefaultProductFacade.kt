package com.category.api.facade

import com.category.api.model.ColorSwatch
import com.category.api.model.Product
import com.category.api.model.Products
import com.category.api.service.ProductService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

import java.util.ArrayList
import java.util.logging.Logger
import kotlin.streams.toList

@Component
class DefaultProductFacade @Autowired
constructor(private val productService: ProductService) : ProductFacade {

    companion object {
        val log = Logger.getLogger(DefaultProductFacade::class.simpleName)!!
    }

    /**
     * Populate and return list of discounted products formatted as per given label type
     * @param products: List<Product>
     * @param labelType: String
     */
    override fun populateDiscountedProductsData(products: List<Product>, labelType: String): Products {

        log.info("Populating discounted products list... ")
        val sortedDiscountedProducts = Products()

        //filter products having price reduction
        val discountedProducts: List<Product> = products.stream()
                .filter { p -> productService.getPriceReduction(p.price!!) > 0 }.toList()

        if (discountedProducts.isNotEmpty()) {
            //sort by reduction price
            val sortedDscProds = discountedProducts.sortedWith(compareByDescending { p -> productService.getPriceReduction(p.price!!) })

            //format and populate discounted products data
            populateDiscountedProductsData(sortedDscProds, sortedDiscountedProducts, labelType)
            log.info("Returned the list of " + sortedDscProds.size + " formatted discounted products! ")
        } else {
            sortedDiscountedProducts.errorMessage = "No Discounted product available under this category! "
            log.info("No Discounted product available under this category! ")
        }
        return sortedDiscountedProducts
    }


    private fun populateDiscountedProductsData(discountedProducts: List<Product>, sortedDiscountedProducts: Products, labelType: String) {
        val discountedProductsData = ArrayList<Product>()
        discountedProducts.forEach { product ->
            val discountedProduct = Product()
            discountedProduct.productId = product.productId
            discountedProduct.title = product.title
            discountedProduct.nowPrice = productService.formattedPrice(product.price?.now!!, product.price?.currency!!)
            discountedProduct.priceLabel = productService.formatPriceLabel(product.price!!, labelType)
            populateColorSwatches(discountedProduct, product.colorSwatches!!)
            discountedProductsData.add(discountedProduct)
        }
        sortedDiscountedProducts.products = discountedProductsData
    }

    private fun populateColorSwatches(sortedProductData: Product, colorSwatches: List<ColorSwatch>) {
        val fmtColorSwatches = ArrayList<ColorSwatch>()
        colorSwatches.forEach { colorSwatch ->
            val fmtColorSwatch = ColorSwatch()
            fmtColorSwatch.color = colorSwatch.color
            fmtColorSwatch.rgbColor = productService.getHexColor(colorSwatch.basicColor)
            fmtColorSwatch.skuId = colorSwatch.skuId
            fmtColorSwatches.add(fmtColorSwatch)
        }
        sortedProductData.colorSwatches = fmtColorSwatches
    }

}
