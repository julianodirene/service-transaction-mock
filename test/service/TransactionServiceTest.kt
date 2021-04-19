package com.jdirene.transaction.mock.service

import com.jdirene.transaction.mock.domain.Transaction
import com.jdirene.transaction.mock.repository.TransactionRepository
import io.ktor.features.*
import io.mockk.every
import io.mockk.mockk
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject
import kotlin.test.*

class TransactionServiceTest : KoinTest {

    private val repositoryMock = mockk<TransactionRepository>()

    private val service: TransactionService by inject()

    @BeforeTest
    fun setup() {
        startKoin {
            modules(
                module {
                    single { repositoryMock }
                    single { TransactionService(get()) }
                }
            )
        }
    }

    @AfterTest
    fun afterTest(){
        stopKoin()
    }

    @Test
    fun `getTransactionsByUserAndMonth should return transaction list when valid arguments given`() {
        val transactionList = listOf(
            Transaction("Transaction description", 123123L, 99980),
            Transaction("Second Transaction description", 133453L, 997770)
        )
        every { repositoryMock.getTransactionsByUserAndMonth(any(), any(), any()) } returns transactionList

        val response = service.getTransactionsByUserAndMonth(1500, 2021, 4)

        assertEquals(transactionList, response)
    }

    @Test
    fun `getTransactionsByUserAndMonth should throw BadRequestException when invalid userId given`() {
        assertFailsWith(BadRequestException::class){
            service.getTransactionsByUserAndMonth(150, 2021,4)
        }
        assertFailsWith(BadRequestException::class){
            service.getTransactionsByUserAndMonth(150000, 2021,4)
        }
    }

    @Test
    fun `getTransactionsByUserAndMonth should throw BadRequestException when invalid year given`() {
        assertFailsWith(BadRequestException::class){
            service.getTransactionsByUserAndMonth(1340, 1000000000,4)
        }
    }

    @Test
    fun `getTransactionsByUserAndMonth should throw BadRequestException when invalid month given`() {
        assertFailsWith(BadRequestException::class){
            service.getTransactionsByUserAndMonth(1340, 2021,13)
        }
    }
}