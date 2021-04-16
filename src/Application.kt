package com.jdirene.transaction.mock

import com.jdirene.transaction.mock.controller.transactionRoutes
import com.jdirene.transaction.mock.di.appModule
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.gson.*
import io.ktor.routing.*
import org.koin.ktor.ext.Koin
import org.koin.logger.slf4jLogger

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {
    install(Koin) {
        slf4jLogger()
        modules(appModule)
    }
    install(ContentNegotiation) {
        gson {
        }
    }

    routing {
        transactionRoutes()
    }
}

