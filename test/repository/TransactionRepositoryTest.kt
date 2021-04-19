package com.jdirene.transaction.mock.repository

import com.jdirene.transaction.mock.domain.Transaction
import com.jdirene.transaction.mock.generator.TransactionGenerator
import com.jdirene.transaction.mock.service.TransactionService
import io.ktor.features.*
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject
import kotlin.test.*

class TransactionRepositoryTest : KoinTest {

    private val transactionGeneratorMock = mockk<TransactionGenerator>()

    private val repository: TransactionRepository by inject()

    @BeforeTest
    fun setup() {
        startKoin {
            modules(
                module {
                    single { transactionGeneratorMock }
                    single { TransactionRepository(get()) }
                }
            )
        }
    }

    @AfterTest
    fun afterTest(){
        stopKoin()
    }

    @Test
    fun `getTransactionsByUserAndMonth should return  transactionGenerator`() {
        val transactionList = listOf(
            Transaction("Transaction description", 123123L, 99980),
            Transaction("Second Transaction description", 133453L, 997770)
        )
        every { transactionGeneratorMock.generateTransactionsByUserAndMonth(any(), any(), any()) } returns transactionList

        val response = repository.getTransactionsByUserAndMonth(1500, 2021, 4)

        assertEquals(transactionList, response)
        verify {
            transactionGeneratorMock.generateTransactionsByUserAndMonth(1500, 2021, 4)
        }
    }
}