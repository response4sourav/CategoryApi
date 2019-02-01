package com.category.api.client

import com.category.api.client.configurations.FeignConfigurations
import com.category.api.model.Products
import com.netflix.hystrix.HystrixCommand
import feign.Response
import feign.Util
import feign.codec.Decoder
import feign.codec.ErrorDecoder
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import java.lang.Exception

@FeignClient(name = "categoryService", url = "\${clients.category.url}", configuration = [FeignConfigurations.CategoryApi::class])
interface CategoryApiClient {

    @RequestMapping(method = [RequestMethod.GET], value = ["\${clients.category.path}"], name = "categoryApiClient" )
    fun getProductsInCategory(@PathVariable("categoryId") categoryId: String): HystrixCommand<Products>
}


open class CategoryApiErrorDecoder(private val decoder: Decoder) : ErrorDecoder {
    private val delegate = ErrorDecoder.Default()

    override fun decode(methodKey: String?, response: Response?): Exception {
        val repeatableBody = Util.toByteArray(response?.body()?.asInputStream())
        val repeatableResponse = response?.toBuilder()?.body(repeatableBody)!!.build()

        return try {
            val categoryApiError = decoder.decode(repeatableResponse, CategoryApiError::class.java) as CategoryApiError
            CategoryApiErrorException(repeatableResponse.status(), categoryApiError)
        } catch (e: Exception) {
            return delegate.decode(methodKey, repeatableResponse)
        }
    }

    class CategoryApiErrorException(val status: Int, val error: CategoryApiError)
        : Exception("Category Api returned status $status with error $error")
}

data class CategoryApiError(val code: String,
                           val message: String?,
                           val errors: List<NestedError>? = null)

data class NestedError(val code: String,
                       val message: String?,
                       val field: String?)