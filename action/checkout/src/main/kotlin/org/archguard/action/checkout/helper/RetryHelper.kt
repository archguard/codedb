package org.archguard.action.checkout

import kotlin.math.floor

const val defaultMaxAttempts = 3
const val defaultMinSeconds = 10
const val defaultMaxSeconds = 20

class RetryHelper(
    val maxAttempts: Int = defaultMaxAttempts,
    val minSeconds: Int = defaultMinSeconds,
    val maxSeconds: Int = defaultMaxSeconds
) {
    private fun getSleepAmount(): Int {
        return (floor(Math.random() * (maxSeconds - minSeconds + 1)) + minSeconds).toInt()
    }

    private fun sleep(seconds: Int): Unit {
        Thread.sleep((seconds * 1000).toLong())
    }

    fun <T> execute(action: () -> T): T {
        var attempt = 1
        while (attempt < maxAttempts) {
            // Try
            try {
                return action()
            } catch (err: Exception) {
                println(err.message)
            }

            // Sleep
            val seconds = getSleepAmount()
            println("Waiting $seconds seconds before trying again")
            sleep(seconds)
            attempt++
        }

        // Last attempt
        return action()
    }
}