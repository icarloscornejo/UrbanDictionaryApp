package com.prototype.data.util

import androidx.lifecycle.LiveDataScope
import androidx.lifecycle.liveData
import com.prototype.domain.model.Resource
import retrofit2.HttpException
import java.io.IOException

class NetworkBoundResource {
    companion object {
        inline fun <Response, Model> create(
            crossinline query: suspend () -> Model?,
            crossinline fetch: suspend LiveDataScope<Resource<Model>>.(Model?) -> Response?,
            crossinline saveFetchResult: suspend (Response) -> Unit,
            crossinline onFetchFailed: (Throwable) -> Unit = {},
            crossinline shouldFetch: (Model?) -> Boolean = { true }
        ) = liveData {
            emit(Resource.loading(null))
            val firstResult = query()

            if (shouldFetch(firstResult)) {
                emit(Resource.loading(firstResult))
                try {
                    val fetchedData = fetch(firstResult)
                    if (fetchedData != null) {
                        saveFetchResult(fetchedData)

                        emit(Resource.success(query()))
                    }
                } catch (ex: HttpException) {
                    onFetchFailed(ex)
                    emit(Resource.error(ex, firstResult))
                } catch (ex: IOException) {
                    onFetchFailed(ex)
                    emit(Resource.error(ex, firstResult))
                }
            } else {
                emit(Resource.success(firstResult))
            }
        }
    }
}