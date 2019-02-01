package com.category.api.service

import com.category.api.client.CategoryApiClient
import com.category.api.model.ColorSwatch
import com.category.api.model.Product
import com.category.api.model.Products
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.ArrayList
import kotlin.streams.toList

@Service
class DefaultCategoryService @Autowired
constructor(private val categoryApiClient: CategoryApiClient,
            private val productService: ProductService) : CategoryService {


    companion object {
        val LOG: Logger = LoggerFactory.getLogger(CategoryService::class.java)
    }

    override fun getDiscountedProductForCategory(categoryId: String, labelType: String): Products {

        val products = getProductsInCategory(categoryId)
        return populateDiscountedProductsData(products.products!!, labelType)
    }


    private fun getProductsInCategory(categoryId: String): Products {

        return categoryApiClient.getProductsInCategory(categoryId).execute()
    }

    private fun populateDiscountedProductsData(products: List<Product>, labelType: String): Products {

        LOG.info("Populating discounted products list... ")

        //filter products having price reduction
        val discountedProducts: List<Product> = products.stream()
                .filter { p -> productService.getPriceReduction(p.price!!) > 0 }.toList()

        return if (discountedProducts.isNotEmpty()) {
            //sort by reduction price
            val sortedDscProds = discountedProducts.sortedWith(compareByDescending { p -> productService.getPriceReduction(p.price!!) })

            //format and populate discounted products data
            LOG.info("Returned the list of " + sortedDscProds.size + " formatted discounted products! ")
            getDiscountedProductsData(sortedDscProds, labelType)

        } else {
            LOG.info("No Discounted product available under this category! ")
            Products(errorMessage = "No Discounted product available under this category! ")
        }
    }

    private fun getDiscountedProductsData(discountedProducts: List<Product>, labelType: String): Products {
        val discountedProductsData = ArrayList<Product>()
        discountedProducts.forEach { p ->
            discountedProductsData.add(
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

    private fun populateColorSwatches(colorSwatches: List<ColorSwatch>): List<ColorSwatch> {
        val fmtColorSwatches = ArrayList<ColorSwatch>()
        colorSwatches.forEach { cs ->
            fmtColorSwatches.add(
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
