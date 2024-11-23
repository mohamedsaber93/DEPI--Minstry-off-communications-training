package com.model

data class SortedTimeInterval(
    val result: List<ResultInterval>,
    val asyncState: Any,
    val creationOptions: Int,
    val exception: Any,
    val id: Int,
    val isCanceled: Boolean,
    val isCompleted: Boolean,
    val isCompletedSuccessfully: Boolean,
    val isFaulted: Boolean,
    val status: Int
)

data class ResultInterval(
    val id:Int,
    val doctorId: String,
    val intervalStart:String,
    val intervalEnd:String
)