package com.prototype.domain.model

// This class is used to simplify the way to emit the result on any call

class Resource<out T> private constructor(
    val status: Status,
    val data: T?,
    val error: Throwable?
) {

    enum class Status {
        SUCCESS,
        ERROR,
        LOADING
    }

    companion object {
        fun <T> success(data: T?) = Resource(
            Status.SUCCESS,
            data,
            null
        )

        fun <T> error(error: Throwable, data: T?) =
            Resource(
                Status.ERROR,
                data,
                error
            )

        fun <T> loading(data: T?) = Resource(
            Status.LOADING,
            data,
            null
        )
    }
}