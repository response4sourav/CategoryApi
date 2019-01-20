package com.category.api.service

import com.category.api.RestTemplateErrorHandler
import com.category.api.model.Products
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.http.*
import org.springframework.stereotype.Service
import org.springframework.web.client.RestClientException
import org.springframework.web.client.RestTemplate
import java.time.Duration
import java.util.logging.Logger

@Service
class DefaultCategoryService @Autowired
constructor(restTemplateBuilder: RestTemplateBuilder) : CategoryService {

    private val restTemplate: RestTemplate

    @Value("\${category.base.url}")
    private val baseUrl: String = ""

    @Value("\${category.product.endpoint}")
    private val productPath: String = ""

    @Value("\${category.api.key}")
    private val apiKey: String = ""

    @Value("\${category.api.connection.timeout}")
    private val connectionTimeout: Duration = Duration.ofSeconds(5)

    @Value("\${category.api.read.timeout}")
    private val readTimeout: Duration = Duration.ofSeconds(15)

    companion object {
        val log = Logger.getLogger(DefaultCategoryService::class.simpleName)!!
    }

    init {
        this.restTemplate = restTemplateBuilder
                .setConnectTimeout(connectionTimeout)
                .setReadTimeout(readTimeout)
                .errorHandler(RestTemplateErrorHandler())
                .build()
    }

    override fun getProductsForCategory(categoryId: String): Products {

        val url = StringBuilder(baseUrl)
        url.append(categoryId).append(productPath).append(apiKey)
        log.info("Trying to fetch products from category api endpoint : " + url.toString())

        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON
        val entity = HttpEntity<String>(headers)
        var products = Products()

        try {
            val response = restTemplate.exchange(url.toString(), HttpMethod.GET, entity, Products::class.java)
            if (HttpStatus.OK == response.statusCode) {
                products = response.body!!
                log.info("Successfully fetched products from category api endpoint!")
            }

        } catch (ex: RestClientException) {
            log.severe("Error occurred while calling category api endpoint $ex")
            products.errorMessage = "Error occurred while calling api endpoint " + ex.message
        }

        return products
    }

}
