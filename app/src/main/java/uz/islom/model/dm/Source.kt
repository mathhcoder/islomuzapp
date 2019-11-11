package uz.islom.model.dm

sealed class Source {
    class ERROR() : Source()
    class NETWORK(val isFullyLoaded: Boolean) : Source()
    class DATABASE(val isFullyLoaded: Boolean) : Source()
}