package com.category.api

import org.springframework.http.HttpStatus
import org.springframework.http.client.ClientHttpResponse
import org.springframework.stereotype.Component
import org.springframework.web.client.ResponseErrorHandler

import java.io.IOException

import org.springframework.http.HttpStatus.Series.CLIENT_ERROR
import org.springframework.http.HttpStatus.Series.SERVER_ERROR
import java.util.logging.Logger

@Component
class RestTemplateErrorHandler : ResponseErrorHandler {

    companion object {
        val log = Logger.getLogger(RestTemplateErrorHandler::class.simpleName)!!
    }

    @Throws(IOException::class)
    override fun hasError(httpResponse: ClientHttpResponse): Boolean {

        return httpResponse.statusCode.series() == CLIENT_ERROR || httpResponse.statusCode.series() == SERVER_ERROR
    }

    @Throws(IOException::class)
    override fun handleError(httpResponse: ClientHttpResponse) {

        if (httpResponse.statusCode
                        .series() == HttpStatus.Series.SERVER_ERROR) {
            log.severe("Server side processing error, probably connection errors!")
        } else if (httpResponse.statusCode
                        .series() == HttpStatus.Series.CLIENT_ERROR) {
            log.severe("Client side processing error, probably empty api response body returned!")
        }
    }
}
