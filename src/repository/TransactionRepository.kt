package com.jdirene.transaction.mock.repository

import com.jdirene.transaction.mock.domain.Transaction
import com.jdirene.transaction.mock.generator.TransactionGenerator

class TransactionRepository(private val transactionGenerator: TransactionGenerator) {
    fun getTransactionsByUserAndMonth(userId: Int, year: Int, month: Int): List<Transaction> {
        return transactionGenerator.generateTransactionsByUserAndMonth(userId, year, month)
    }
}