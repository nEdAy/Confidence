package cn.neday.sheep.model

data class Response<out T>(val errorCode: Int, val errorMsg: String, val data: T)