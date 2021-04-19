package com.jdirene.transaction.mock

import io.ktor.application.*
import io.ktor.response.*
import io.ktor.request.*
import io.ktor.routing.*
import io.ktor.http.*
import io.ktor.gson.*
import io.ktor.features.*
import kotlin.test.*
import io.ktor.server.testing.*

class ApplicationTest {
    @Test
    fun testHealth() {
        withTestApplication({ module(testing = true) }) {
            handleRequest(HttpMethod.Get, "/health").apply {
                assertEquals(HttpStatusCode.OK, response.status())
                assertEquals("{\"status\":\"up\"}", response.content)
            }
        }
    }
}
