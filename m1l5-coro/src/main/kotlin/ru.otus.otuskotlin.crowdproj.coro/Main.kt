package ru.otus.otuskotlin.crowdproj.coro

import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import java.util.concurrent.Executors

suspend fun main() {
    val numbers = (0 until 1000000).map {
        (0..100).random()
    }
    val dispatcher = Executors.newFixedThreadPool(10).asCoroutineDispatcher()

    runBlocking {
        val startTime = System.currentTimeMillis()
        val res = async(dispatcher) {
            (0 until 1000).map { t ->
                var sum = 0
                for (i in 0 until 1000) {
                    sum += numbers[i + (t * 1000)]
                }
                sum
            }
        }
        val sum = res.await().sum()
        println("10 threads time: ${System.currentTimeMillis() - startTime}. Result: $sum")
    }

    val startTime = System.currentTimeMillis()
    var res = 0
    for(i in 0 until 1000000) {
        res += numbers[i]
    }
    println("10 threads time: ${System.currentTimeMillis() - startTime}. Result: $res")
}