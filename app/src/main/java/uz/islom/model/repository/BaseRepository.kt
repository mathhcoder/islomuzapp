package uz.islom.model.repository

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import uz.islom.BuildConfig
import java.util.concurrent.TimeUnit

abstract class BaseRepository {

    companion object {
        private var okClient: OkHttpClient? = null
    }




}