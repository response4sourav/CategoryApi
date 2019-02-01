package com.category.api.client.configurations.hystrix

import com.netflix.hystrix.strategy.HystrixPlugins
import com.netflix.hystrix.strategy.concurrency.HystrixConcurrencyStrategy
import org.springframework.stereotype.Component
import java.util.concurrent.Callable
import javax.annotation.PostConstruct

class HystrixConfiguration {

    @PostConstruct
    fun configureHystrixConcurrencyStrategy() {
        HystrixPlugins.getInstance().registerConcurrencyStrategy(CorrelationIdPreservingConcurrencyStrategy())
    }

    @Component
    class CorrelationIdPreservingConcurrencyStrategy : HystrixConcurrencyStrategy() {

        override fun <T> wrapCallable(callable: Callable<T>): Callable<T> {

            return Callable {
                    return@Callable callable.call()
            }
        }
    }

}