package com.jdirene.transaction.mock.controller

import com.google.gson.Gson
import com.jdirene.transaction.mock.domain.Transaction
import com.jdirene.transaction.mock.module
import com.jdirene.transaction.mock.service.TransactionService
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.server.testing.*
import io.mockk.every
import io.mockk.mockk
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import kotlin.test.*

class TransactionControllerTest : KoinTest {

    private val serviceMock = mockk<TransactionService>()

    @BeforeTest
    fun setup() {
        startKoin {
            modules(
                module {
                    single { serviceMock }
                }
            )
        }
    }

    @AfterTest
    fun afterTest() {
        stopKoin()
    }

    @Test
    fun `getTransactionsByUserAndMonth should return transaction list when valid arguments given`() {
        val transactionList = listOf(
            Transaction("Transaction description", 123123L, 99980),
            Transaction("Second Transaction description", 133453L, 997770)
        )
        every { serviceMock.getTransactionsByUserAndMonth(any(), any(), any()) } returns transactionList
        val expectedTransactionList = Gson().toJson(transactionList)

        testGetTransactionsByUserAndMonth(
            uri = "/1500/transacoes/2021/4",
            expectedStatus = HttpStatusCode.OK,
            expectedContent = expectedTransactionList
        )
    }

    @Test
    fun `getTransactionsByUserAndMonth should return status 400 when non integer userId given`() {
        testGetTransactionsByUserAndMonth(
            uri = "/15a00/transacoes/2021/4",
            expectedStatus = HttpStatusCode.BadRequest,
            expectedContent = "{\"code\":400,\"message\":\"User ID, Year and Month must to be Integer.\"}"
        )
    }

    @Test
    fun `getTransactionsByUserAndMonth should return status 400 when non integer year given`() {
        testGetTransactionsByUserAndMonth(
            uri = "/1500/transacoes/202b1/4",
            expectedStatus = HttpStatusCode.BadRequest,
            expectedContent = "{\"code\":400,\"message\":\"User ID, Year and Month must to be Integer.\"}"
        )
    }

    @Test
    fun `getTransactionsByUserAndMonth should return status 400 when non integer month given`() {
        testGetTransactionsByUserAndMonth(
            uri = "/1500/transacoes/2021/4c",
            expectedStatus = HttpStatusCode.BadRequest,
            expectedContent = "{\"code\":400,\"message\":\"User ID, Year and Month must to be Integer.\"}"
        )
    }

    @Test
    fun `getTransactionsByUserAndMonth should return status 400 when transaction service throws BadRequestException`() {

        every {
            serviceMock.getTransactionsByUserAndMonth(
                any(),
                any(),
                any()
            )
        } throws BadRequestException("Invalid User ID.")

        testGetTransactionsByUserAndMonth(
            uri = "/150000/transacoes/2021/4",
            expectedStatus = HttpStatusCode.BadRequest,
            expectedContent = "{\"code\":400,\"message\":\"Invalid User ID.\"}"
        )
    }

    private fun testGetTransactionsByUserAndMonth(uri: String, expectedStatus: HttpStatusCode, expectedContent: String) {
        withTestApplication({ module(testing = true) }) {
            handleRequest(HttpMethod.Get, uri).apply {
                assertEquals(expectedStatus, response.status())
                assertEquals(expectedContent, response.content)
            }
        }
    }
}