package com.category.api

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.PropertySource

@SpringBootApplication
@PropertySource(value = ["classpath:/application.properties"])
class ApiApplication

fun main(args: Array<String>) {
    runApplication<ApiApplication>(*args)
}


