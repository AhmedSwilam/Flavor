package kw.com.pixel.flavor.data.api

import android.util.Log
import com.google.android.datatransport.runtime.dagger.Provides
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
import javax.inject.Singleton


class RetrofitInstance {

    companion object {
        private val retrofit by lazy {
            val logging = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)
            val client = OkHttpClient.Builder()
                .addInterceptor(logging)
                .connectTimeout(100, TimeUnit.SECONDS)
                .readTimeout(100,TimeUnit.SECONDS)
                .addInterceptor(logging)
                .addInterceptor(providesApiKeyInterceptor())
                .build()

            val gson = GsonBuilder()
                .setLenient()
                .create()
            Retrofit.Builder()
                .baseUrl("https://drgloosy.net/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build()

        }

        val api by lazy {
            retrofit.create(ApiService::class.java)
        }

        @ApiKeyInterceptorOkHttpClient
        @Singleton
        @Provides
        fun providesApiKeyInterceptor(): Interceptor = ApiKeyInterceptor()


        private fun provideOkHttpClient(interceptor: HttpLoggingInterceptor): OkHttpClient {
            val b = OkHttpClient.Builder()
            b.addInterceptor(interceptor)
            return b.build()
        }

        private fun provideLoggingInterceptor(): HttpLoggingInterceptor {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            return interceptor
        }
    }
    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class ApiKeyInterceptorOkHttpClient

    class ApiKeyInterceptor : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {

            val original: Request = chain.request()
            val httpUrl: HttpUrl = original.url

            val url: HttpUrl = httpUrl.newBuilder()
                .build()
            val request: Request = original.newBuilder()
                .addHeader("CountryId","")
                .addHeader("CurrencyId","")
                .addHeader("TimeZone","")
                .addHeader("DeviceSN","")
                .addHeader("FirebaseToken","")
                .addHeader("LanguageId","")
                .addHeader("User-Agent","DrGloosy/android")

                .url(url).build()
              Log.d("Retrofit", request.headers.toString());
            return chain.proceed(request)
        }
    }


}