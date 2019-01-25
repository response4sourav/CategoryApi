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

        //filter products having price reduction
        val discountedProducts: List<Product> = products.stream()
                .filter { p -> productService.getPriceReduction(p.price!!) > 0 }.toList()

        return if (discountedProducts.isNotEmpty()) {
            //sort by reduction price
            val sortedDscProds = discountedProducts.sortedWith(compareByDescending { p -> productService.getPriceReduction(p.price!!) })

            //format and populate discounted products data
            log.info("Returned the list of " + sortedDscProds.size + " formatted discounted products! ")
            getDiscountedProductsData(sortedDscProds, labelType)

        } else {
            log.info("No Discounted product available under this category! ")
            Products(errorMessage = "No Discounted product available under this category! ")

        }
    }

    private fun getDiscountedProductsData(discountedProducts: List<Product>, labelType: String) : Products {
        val discountedProductsData = ArrayList<Product>()
        discountedProducts.forEach { p ->
            discountedProductsData.add (
                    Product(
                            title = p.title,
                            productId = p.productId,
                            nowPrice = productService.formattedPrice(p.price?.now!!, p.price?.currency!!),
                            priceLabel = productService.formatPriceLabel(p.price!!, labelType),
                            colorSwatches = populateColorSwatches(p.colorSwatches!!)
                    )
            )
        }
        return Products(products = discountedProductsData)
    }

    private fun populateColorSwatches(colorSwatches: List<ColorSwatch>) : List<ColorSwatch> {
        val fmtColorSwatches = ArrayList<ColorSwatch>()
        colorSwatches.forEach { cs ->
            fmtColorSwatches.add (
                   ColorSwatch(
                        color = cs.color,
                        skuId = cs.skuId,
                        rgbColor = productService.getHexColor(cs.basicColor)
                   )
           )
        }
        return fmtColorSwatches
    }

}
