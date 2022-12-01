package org.archguard.codedb.orchestration.io

sealed class HttpMethod {
    object GET : HttpMethod()
    object POST : HttpMethod()
    object PUT : HttpMethod()
    object DELETE : HttpMethod()
    object PATCH : HttpMethod()
    object HEAD : HttpMethod()
    object OPTIONS : HttpMethod()
    object TRACE : HttpMethod()
    object CONNECT : HttpMethod()
}