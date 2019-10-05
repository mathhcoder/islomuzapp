package uz.islom.io.net

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import uz.islom.BuildConfig
import java.util.concurrent.TimeUnit

object ApiFactory {

    private var okClient: OkHttpClient? = null
    private var service: ApiService? = null

    fun apiService(): ApiService {
        var service: ApiService? = service
        if (service == null) {
            synchronized(ApiFactory::class.java) {
                service = this.service
                if (service == null) {
                    this.service = buildRetrofit().create(ApiService::class.java)
                    service = this.service
                }
            }
        }
        return service ?: buildRetrofit().create(ApiService::class.java)
    }


    private fun buildRetrofit(): Retrofit {
        return Retrofit.Builder()
                .baseUrl(BuildConfig.SERVER_URL)
                .client(getClient())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
    }

    private fun getClient(): OkHttpClient {
        var c: OkHttpClient? = okClient
        if (c == null) {
            synchronized(ApiFactory::class.java) {
                okClient = getHttpClient()
                c = okClient
            }
        }
        return c ?: getHttpClient()
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
