package com.thelumiereguy.matchesapp.domain.usecases

import com.thelumiereguy.matchesapp.domain.enitity.ErrorModel
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

typealias CompletionBlock<T> = BaseUseCase.Request<T>.() -> Unit

abstract class BaseUseCase<T> {

    private var parentJob: Job = Job()
    private var backgroundCoroutineContext: CoroutineContext = Dispatchers.IO
    private var foregroundCoroutineContext: CoroutineContext = Dispatchers.Main


    /**
     * Each Use Case must adhere to this contract
     * The implementation will be executed in background
     */
    protected abstract suspend fun executeOnBackground(): T


    /**
     * Cancels all ongoing Jobs assigns the new one.
     *
     * @param completionBlock - This contains implementation for both onSuccess and onError
     */

    fun execute(completionBlock: CompletionBlock<T>) {
        val response = Request<T>().apply { completionBlock() }
        unsubscribe()
        parentJob = Job()
        CoroutineScope(foregroundCoroutineContext + parentJob).launch {
            try {
                val result = withContext(backgroundCoroutineContext) {
                    executeOnBackground()
                }
                response(result)
            } catch (e: Exception) {
                response(
                    ErrorModel(
                        e,
                        e.message,
                        -1
                    )
                )
            }
        }
    }


    /**
     * Cancels all the ongoing Jobs
     */
    fun unsubscribe() {
        parentJob.apply {
            cancelChildren()
            cancel()
        }
    }


    /**
     * @onComplete - takes in Success Block
     * @onError - takes in onError Block
     */
    class Request<T> {
        private var onComplete: ((T) -> Unit)? = null
        private var onError: ((ErrorModel) -> Unit)? = null

        fun onComplete(block: (T) -> Unit) {
            onComplete = block
        }

        fun onError(block: (ErrorModel) -> Unit) {
            onError = block

        }

        operator fun invoke(result: T) {
            onComplete?.invoke(result)
        }

        operator fun invoke(error: ErrorModel) {
            onError?.invoke(error)
        }
    }
}