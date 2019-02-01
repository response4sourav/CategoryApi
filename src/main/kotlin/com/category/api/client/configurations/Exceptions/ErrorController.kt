package com.category.api.client.configurations.Exceptions

import com.category.api.client.CategoryApiErrorDecoder
import com.category.api.client.configurations.hystrix.isCircuitBreakOpen
import com.category.api.client.configurations.hystrix.logException
import com.netflix.hystrix.exception.HystrixBadRequestException
import com.netflix.hystrix.exception.HystrixRuntimeException
import feign.FeignException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.annotation.*
import java.net.ConnectException
import java.util.concurrent.TimeoutException


@ControllerAdvice
class ExceptionHandlingAdvice {

    companion object {
        val LOG: Logger = LoggerFactory.getLogger(ExceptionHandlingAdvice::class.java)
    }

    @ExceptionHandler(HystrixRuntimeException::class, HystrixBadRequestException::class)
    @ResponseBody
    fun handleHystrixException(e: RuntimeException): Errors {
        logException("Category API Service", e)

        val cause = e.cause
        return when {
            cause is TimeoutException -> Errors(503, "Category API timeout")
            cause is CategoryApiErrorDecoder.CategoryApiErrorException -> Errors(cause.status, descriptionFor(cause))
            cause is FeignException && cause.cause is ConnectException -> Errors(503, "Category API connection error")
            cause is FeignException -> Errors(if (cause.status() > 0) cause.status() else 500, cause.message)
            isCircuitBreakOpen(cause) -> Errors(503, "Category API circuit breaker open")
            else -> Errors(500, descriptionFor(e))
        }
    }

    @ExceptionHandler(value = [HttpMessageNotReadableException::class])
    @ResponseBody
    fun handleHttpMessageNotReadableException(e: HttpMessageNotReadableException): Errors {
        LOG.warn("Unreadable request submitted ${e.message}")
        return Errors(400, e.message.orEmpty())
    }

    @ExceptionHandler(value = [Exception::class])
    @ResponseBody
    fun handleUnknownException(e: Exception): Errors {
        val cause = e.cause
        return if (cause is HystrixRuntimeException || cause is HystrixBadRequestException) {
            handleHystrixException(cause as RuntimeException)
        } else {
            LOG.error("Unknown exception occurred ${e.message}")
            Errors(500, descriptionFor(e))
        }
    }

    private fun descriptionFor(e: CategoryApiErrorDecoder.CategoryApiErrorException) = with(e.error) {
        val nestedErrors: String? = errors?.joinToString(prefix = " [", transform = { it.message.orEmpty() }, postfix = "]")
        "$code: $message${nestedErrors.orEmpty()}"
    }

    private fun descriptionFor(e: Exception) = "${e.javaClass.simpleName}: ${e.message}"
}