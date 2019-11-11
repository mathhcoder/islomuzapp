package uz.islom.vm

import androidx.lifecycle.ViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import uz.islom.BuildConfig
import uz.islom.IslomUzApp
import uz.islom.model.AppPreferenceManager
import uz.islom.model.AppStorageManager
import java.util.concurrent.TimeUnit

open class BaseViewModel : ViewModel() {

    companion object {

        internal val networkManager by lazy {
            Retrofit.Builder()
                    .baseUrl(BuildConfig.SERVER_URL)
                    .client(getHttpClient())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build()
        }

        internal val storageManager by lazy {
            AppStorageManager.instance
        }

        internal val preferenceManager by lazy {
            AppPreferenceManager(IslomUzApp.getInstance())
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


}