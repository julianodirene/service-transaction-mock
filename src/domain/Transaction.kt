package com.jdirene.transaction.mock.domain

import com.google.gson.annotations.SerializedName

data class Transaction(
    @SerializedName("descricao")
    val description: String,

    @SerializedName("data")
    val date: Long,

    @SerializedName("valor")
    val value: Int
) {
    companion object {
        const val MIN_DESCRIPTION_LENGTH = 10
        const val MAX_DESCRIPTION_LENGTH = 60
        const val MIN_VALUE = -9999999
        const val MAX_VALUE = 9999999
    }
}
