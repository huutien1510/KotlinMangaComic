package vn.iotstar.appdoctruyen.API

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    var retrofit: Retrofit? = null
        get() {
            if (field == null) {
                val gson = GsonBuilder().setDateFormat("dd-MM-yyyy").create()
                field = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build()
            }
            return field
        }
        private set
    private const val BASE_URL = "http://192.168.1.76:8090/"
    val apiService: APIService?
        get() = BaseClient.createService(APIService::class.java, BASE_URL)
}
