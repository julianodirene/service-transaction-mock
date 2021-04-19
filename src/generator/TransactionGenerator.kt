package com.jdirene.transaction.mock.generator

import com.jdirene.transaction.mock.domain.Transaction
import java.time.LocalDate
import java.time.YearMonth
import java.time.ZoneId
import java.time.ZoneOffset
import kotlin.random.Random

class TransactionGenerator(private val textGenerator: TextGenerator) {

    fun generateTransactionsByUserAndMonth(userId: Int, year: Int, month: Int): List<Transaction> {
        val transactionList = mutableListOf<Transaction>()
        val length = calcTransactionListLength(userId, month)

        for (i in 1..length) {
            transactionList.add(
                generateTransaction(userId, year, month, i)
            )
        }
        return transactionList
    }

    private fun calcTransactionListLength(userId: Int, month: Int): Int {
        return Character.getNumericValue(userId.toString().first()) * month
    }

    private fun generateTransaction(userId: Int, year: Int, month: Int, transactionIndex: Int): Transaction {
        val date = generateDate(userId, year, month, transactionIndex)
        val value = generateValue(userId, month, transactionIndex)
        return Transaction(
            description = generateDescription(userId, date, value),
            date = date,
            value = value
        )
    }

    private fun generateDescription(userId: Int, date: Long, value: Int): String {
        val seed = userId * date * value
        return textGenerator.generateText(seed.toInt(), Transaction.MIN_DESCRIPTION_LENGTH, Transaction.MAX_DESCRIPTION_LENGTH)
    }

    private fun generateDate(userId: Int, year: Int, month: Int, transactionIndex: Int): Long {
        val yearMonth = YearMonth.of(year, month)
        val random = Random(userId * year * month * transactionIndex)
        val day = random.nextInt(1, yearMonth.lengthOfMonth())

        return LocalDate.of(year, month, day).atStartOfDay().toInstant(ZoneOffset.UTC).toEpochMilli()
    }

    private fun generateValue(userId: Int, month: Int, transactionIndex: Int): Int {
        val random = Random(userId * month * transactionIndex)
        return random.nextInt(Transaction.MIN_VALUE, Transaction.MAX_VALUE)
    }
}