package com.jdirene.transaction.mock.service

import com.jdirene.transaction.mock.domain.Transaction
import com.jdirene.transaction.mock.repository.TransactionRepository
import io.ktor.features.*
import java.time.Year

class TransactionService(private val repository: TransactionRepository) {

    private val minUserId = 1000
    private val maxUserId = 100000

    fun getTransactionsByUserAndMonth(userId: Int, year: Int, month: Int): List<Transaction> {
        if(!isValidUserId(userId)){
            throw BadRequestException("Invalid User ID.")
        }

        if(!isValidYear(year)){
            throw BadRequestException("Invalid Year.")
        }

        if(!isValidMonth(month)){
            throw BadRequestException("Invalid Month.")
        }

        return repository.getTransactionsByUserAndMonth(userId, year, month)
    }

    private fun isValidUserId(userId: Int) = userId in minUserId..maxUserId
    private fun isValidYear(year: Int) = year in Year.MIN_VALUE..Year.MAX_VALUE
    private fun isValidMonth(month: Int) = month in 1..12
}