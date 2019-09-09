package uz.islom.util

import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Action
import io.reactivex.functions.Consumer
import io.reactivex.internal.functions.Functions
import timber.log.Timber

/**
 * developer -> Qodiriy
 * project -> App
 * date -> 21 February 2019
 * github -> github.com/qodiriy
 */

val defaultErrorHandler = Consumer { t: Throwable -> Timber.e(t) }

fun <T> Single<T>.subscribeKt(
        next: Consumer<in T> = Functions.emptyConsumer(),
        error: Consumer<in Throwable> = defaultErrorHandler): Disposable {

    return subscribe(next, error)
}

fun <T> Flowable<T>.subscribeKt(
        next: Consumer<in T> = Functions.emptyConsumer(),
        error: Consumer<in Throwable> = defaultErrorHandler,
        complete: Action = Functions.EMPTY_ACTION): Disposable {

    return subscribe(next, error, complete)
}

fun <T> Observable<T>.subscribeKt(
        next: Consumer<in T> = Functions.emptyConsumer(),
        error: Consumer<in Throwable> = defaultErrorHandler,
        complete: Action = Functions.EMPTY_ACTION): Disposable {

    return subscribe(next, error, complete)
}
