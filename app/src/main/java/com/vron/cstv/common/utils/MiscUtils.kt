package com.vron.cstv.common.utils

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.TimeoutCancellationException

public suspend inline fun <R> runSuspendCatching(block: () -> R): Result<R> {
    return try {
        Result.success(block())
    } catch (t: TimeoutCancellationException) {
        Result.failure(t)
    } catch (c: CancellationException) {
        throw c
    } catch (e: Throwable) {
        Result.failure(e)
    }
}

