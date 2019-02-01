package com.category.api.client.configurations

import com.category.api.client.CategoryApiErrorDecoder
import feign.*
import feign.codec.Decoder
import feign.codec.ErrorDecoder
import feign.okhttp.OkHttpClient
import okhttp3.ConnectionPool
import okhttp3.Interceptor
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level.NONE
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import java.lang.Exception
import java.util.concurrent.TimeUnit.*

class FeignConfigurations {

    open class CategoryApi : Default() {
        @Bean
        @ConfigurationProperties(prefix = "clients.category")
        fun httpClientProperties(): FeignClientProperties = FeignClientProperties("category")

        @Bean
        fun errorDecoder(decoder: Decoder) = HystrixBadRequestWrappingDecoder(
                delegate = CategoryApiErrorDecoder(decoder)
        )

        /*@Bean
        fun requestInterceptor(@Value("\${clients.category.apikey}") key: String) = RequestInterceptor {
            fun apply(request: RequestTemplate) {
                request.query("key", key)
            }
        }*/
    }

    @EnableConfigurationProperties
    open class Default {
        @Bean
        fun options(properties: FeignClientProperties) = Request.Options(properties.connectTimeout.toInt(), properties.readTimeout.toInt())

        @Bean
        fun httpLoggingInterceptor(properties: FeignClientProperties): HttpLoggingInterceptor {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = properties.loggingLevel
            return loggingInterceptor
        }

        @Bean
        fun client(properties: FeignClientProperties, networkInterceptors: List<Interceptor>): OkHttpClient {
            val builder = okhttp3.OkHttpClient.Builder()
                    .writeTimeout(properties.writeTimeout, MILLISECONDS)
                    .connectionPool(ConnectionPool(properties.maxIdleConnections, properties.keepAliveDuration, MILLISECONDS))
            networkInterceptors.forEach { builder.addInterceptor(it) }
            return OkHttpClient(builder.build())
        }

        @Bean
        fun retryer() = Retryer.NEVER_RETRY
    }

    open class HystrixBadRequestWrappingDecoder(private val delegate: ErrorDecoder = ErrorDecoder.Default()) : ErrorDecoder {

        override fun decode(methodKey: String?, response: Response?): Exception {
            return delegate.decode(methodKey, response)
        }
    }

    open class FeignClientProperties(val clientName: String) {
        var writeTimeout: Long = SECONDS.toMillis(10)
        var connectTimeout: Long = SECONDS.toMillis(10)
        var readTimeout: Long = SECONDS.toMillis(10)
        var maxIdleConnections: Int = 5
        var keepAliveDuration: Long = MINUTES.toMillis(5)
        var loggingLevel: HttpLoggingInterceptor.Level = NONE
    }
}