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

    internal fun buildRetrofit(): Retrofit {
        return Retrofit.Builder()
                .baseUrl(BuildConfig.SERVER_URL)
                .client(getClient())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
    }

    private fun getClient(): OkHttpClient {
        if (okClient == null) {
            synchronized(BaseRepository::class.java) {
                okClient = getHttpClient()
            }
        }
        return okClient ?: getHttpClient()
    }

    private fun getHttpClient(): OkHttpClient {

        return OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .addNetworkInterceptor { chain ->
                    val request = chain.request()
                    request.newBuilder()
                            .addHeader("Content-Type", "application/json;charset=utf-8")
                    chain.proceed(request)
                }
                .hostnameVerifier { _, _ -> true }
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60 / 2, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .cache(null)
                .build()

    }


}