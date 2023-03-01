package org.archguard.action.http

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.utils.io.*
import io.ktor.utils.io.core.*
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import org.slf4j.LoggerFactory
import java.io.File
import java.net.URL

private const val TIMEOUT_MILLIS = 1000 * 60 * 5L
private const val MB = 1.0 * 1024 * 1024

/**
 * Wrapper for ktor http client
 */
class HttpClientWrapper {
    val client = HttpClient(CIO) {
        install(HttpTimeout) {
            requestTimeoutMillis = TIMEOUT_MILLIS
        }
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
            })
        }
    }

    suspend inline fun <reified T> get(url: URL): T {
        return client.get(url) {
            contentType(ContentType.Application.Json)
        }.body() as T
    }

    suspend inline fun <reified T>  post(url: URL, body: Any): T {
        return client.post(url) {
            contentType(ContentType.Application.Json)
            setBody(body)
        }.body() as T
    }

    suspend inline fun <reified T> put(url: URL, body: Any): T {
        return client.put(url) {
            contentType(ContentType.Application.Json)
            setBody(body)
        }.body() as T
    }

    suspend fun delete(url: URL): HttpResponse {
        return client.delete(url) {
            contentType(ContentType.Application.Json)
        }
    }

    suspend fun download(url: URL, file: File) {
        runBlocking {
            client.prepareGet(url).execute { httpResponse ->
                val channel: ByteReadChannel = httpResponse.body()
                val totalSize = httpResponse.contentLength()?.div(MB)

                while (!channel.isClosedForRead) {
                    val packet = channel.readRemaining(DEFAULT_BUFFER_SIZE.toLong())
                    while (!packet.isEmpty) {
                        val bytes = packet.readBytes()
                        file.appendBytes(bytes)

                        if (file.length() % MB == 0.0) {
                            logger.info("Received ${file.length() / MB} MB from $totalSize MB ")
                        }
                    }
                }

                logger.info("Received ${file.length()} bytes from ${httpResponse.contentLength()}")
                logger.info("A file saved to ${file.path}")
            }
        }
    }

    companion object {
        private val logger = LoggerFactory.getLogger(HttpClientWrapper::class.java)
    }
}