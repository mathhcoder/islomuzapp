package uz.islom.model.dm

sealed class Result<T> {

    data class SUCCESS<T>(val data: List<T>) : Result<T>()
    data class ERROR<T>(val throwable: Throwable) : Result<T>()
}