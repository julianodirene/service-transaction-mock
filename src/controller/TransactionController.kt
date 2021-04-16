package com.jdirene.transaction.mock.controller

import com.jdirene.transaction.mock.domain.Error
import com.jdirene.transaction.mock.service.TransactionService
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.util.*
import org.koin.ktor.ext.inject

fun Route.transactionRoutes() {
    val service by inject<TransactionService>()

    get("{userId}/transacoes/{year}/{month}") {
        try {
            val userId = call.parameters["userId"]!!.toInt()
            val year = call.parameters["year"]!!.toInt()
            val month = call.parameters["month"]!!.toInt()

            call.respond(service.getTransactionsByUserAndMonth(userId, year, month))

        } catch (e: MissingRequestParameterException) {
            call.respond(HttpStatusCode.BadRequest)
        } catch (e: NumberFormatException) {
            call.respond(HttpStatusCode.BadRequest, Error(
                code = HttpStatusCode.BadRequest.value,
                message = "User ID, Year and Month must to be Integer."
            ))
        } catch (e: BadRequestException) {
            call.respond(HttpStatusCode.BadRequest, Error(
                code = HttpStatusCode.BadRequest.value,
                message = e.message!!
            ))
        }
    }
}