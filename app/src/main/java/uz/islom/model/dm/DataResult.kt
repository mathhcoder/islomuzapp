package uz.islom.model.dm

data class DataResult<T>(val result: Boolean,
                         val dataSource: DataSource,
                         val isFullyLoaded: Boolean,
                         val data: List<T>,
                         val errorMessage : String)