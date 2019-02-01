package com.category.api.client.configurations.hystrix

import com.netflix.hystrix.HystrixCommand
import com.netflix.hystrix.HystrixCommandGroupKey
import com.netflix.hystrix.exception.HystrixRuntimeException
import feign.RetryableException
import feign.hystrix.FallbackFactory
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.util.concurrent.atomic.AtomicBoolean


open class ErrorLoggingFallbackFactory<T>(val clientName: String, val fallbackClient: T) : FallbackFactory<T> {
    companion object {
        val LOG: Logger = LoggerFactory.getLogger(ErrorLoggingFallbackFactory::class.java)
    }

    private val shouldIgnore = AtomicBoolean(true)

    override fun create(cause: Throwable?): T {
        // ignore the first error as it's HystrixTargeter.targetWithFallbackFactory() testing factory's compatibility
        if (!shouldIgnore.compareAndSet(true, false)) {
            logException(clientName, cause)
        }

        return fallbackClient
    }

}

fun <T> wrapObjectInHystrixFallback(returnedObject: T): HystrixCommand<T> {
    return object : HystrixCommand<T>(HystrixCommandGroupKey.Factory.asKey("Category API Service")) {
        override fun run() = returnedObject
    }
}


fun logException(clientName: String, cause: Throwable?) {

    fun isFeignFailedToConnect(cause: Throwable?) : Boolean =
            ((cause is RetryableException) && (cause.message != null) && (cause.message!!.startsWith("Failed to connect to")))

    fun isNoFallbackConnectionFailure(cause: Throwable?) : Boolean =
            ((cause is HystrixRuntimeException) && (cause.message != null) && (cause.message!!.contains("and no fallback available", true))) &&
                    isFeignFailedToConnect(cause.cause)

    when {
        isCircuitBreakOpen(cause) -> ErrorLoggingFallbackFactory.LOG.error("Error calling $clientName - Hystrix circuit breaker is OPEN")
        isFeignFailedToConnect(cause) -> ErrorLoggingFallbackFactory.LOG.error("Error calling $clientName - ${cause?.message}")
        isNoFallbackConnectionFailure(cause) -> ErrorLoggingFallbackFactory.LOG.error("Error calling $clientName - ${cause?.message} - ${cause?.cause?.message}")
        else -> ErrorLoggingFallbackFactory.LOG.error("Error calling $clientName", cause)
    }
}

fun isCircuitBreakOpen(cause: Throwable?) : Boolean =
        (cause is RuntimeException && "hystrix circuit short-circuited and is open".equals(cause.message, true)) ||
                (cause is HystrixRuntimeException && isCircuitBreakOpen(cause.cause))


