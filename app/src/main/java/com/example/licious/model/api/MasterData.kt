package com.example.licious.model.api

data class MasterData(val data: Data, val statusCode: Int, val statusMessage: String) {
    fun isSuccess(): Boolean = (statusCode == 200)
}