package com.jdirene.transaction.mock.generator

import io.ktor.features.*
import io.mockk.every
import io.mockk.justRun
import io.mockk.mockk
import io.mockk.spyk
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject
import kotlin.test.*

class TransactionGeneratorTest : KoinTest {

    private val textGeneratorMock = mockk<TextGenerator>()

    private val transactionGenerator: TransactionGenerator by inject()

    @BeforeTest
    fun setup() {
        startKoin {
            modules(
                module {
                    single { textGeneratorMock }
                    single { TransactionGenerator(get()) }
                }
            )
        }
    }

    @AfterTest
    fun afterTest(){
        stopKoin()
    }




    @Test
    fun `generateTransactionsByUserAndMonth should return a list of transactions`() {
        every { textGeneratorMock.generateText(any(), any(), any()) } answers { "Generated Description" }

        val response = transactionGenerator.generateTransactionsByUserAndMonth(2500, 2021, 4)

        assertEquals(8, response.size)

    }



//    @Test
//    fun `calcTransactionListLength should return first userId digit multiplied by month`() {
//        testCalcTransactionListLength(1500, 7, 7)
//        testCalcTransactionListLength(37800, 3, 9)
//        testCalcTransactionListLength(5500, 5, 25)
//        testCalcTransactionListLength(7500, 2, 14)
//        testCalcTransactionListLength(9500, 1, 9)
//    }
//
//    private fun testCalcTransactionListLength(userId: Int, month: Int, expectedResponse: Int) {
//
//        val transactionGeneratorSpy = spyk(transactionGenerator, recordPrivateCalls = true)
//
//        justRun {
//            val response = transactionGeneratorSpy.invoke("calcTransactionListLength").withArguments(listOf(userId, month))
//
//            assertEquals(expectedResponse, response)
//        }
//
//    }
}