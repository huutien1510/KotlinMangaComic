package vn.iotstar.appdoctruyen.API

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object BaseClient {
    private val sLogging = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    private val sHttpClient: OkHttpClient.Builder = OkHttpClient.Builder()
    fun <S> createService(serviceClass: Class<S>?, baseUrl: String?): S {
        val builder = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
        var retrofit = builder.build()
        if (!sHttpClient.interceptors().contains(sLogging)) {
            sHttpClient.addInterceptor(sLogging)
            builder.client(sHttpClient.build())
            retrofit = builder.build()
        }
        return retrofit.create(serviceClass)
    }
}
