package com.jdirene.transaction.mock.di

import com.jdirene.transaction.mock.generator.TextGenerator
import com.jdirene.transaction.mock.generator.TransactionGenerator
import com.jdirene.transaction.mock.repository.TransactionRepository
import com.jdirene.transaction.mock.service.TransactionService
import org.koin.dsl.module

val appModule = module {
    single { TransactionService(get()) }
    single { TransactionRepository(get()) }
    single { TransactionGenerator(get()) }
    single { TextGenerator() }
}