package com.thelumiereguy.matchesapp.di.modules

import android.app.Application
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.thelumiereguy.matchesapp.BuildConfig
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Type
import javax.inject.Singleton

@Module
object NetworkModule {
    private const val BASE_URL: String = "https://newsapi.org/"

    @Provides
    @Singleton
    fun provideCache(application: Application): Cache =
        Cache(application.cacheDir, 20L * 1024 * 1024) //20 mo

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor() = HttpLoggingInterceptor().apply {
        level =
            if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
            else HttpLoggingInterceptor.Level.NONE
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        cache: Cache,
        httpLoggingInterceptor: HttpLoggingInterceptor,
        requestInterceptor: Interceptor
    ): OkHttpClient =
        OkHttpClient.Builder()
            .cache(cache)
            .addInterceptor(requestInterceptor)
            .addInterceptor(httpLoggingInterceptor)
            .build()


    @Provides
    @Singleton
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    @Singleton
    fun provideGsonConverterFactory(gson: Gson): GsonConverterFactory =
        GsonConverterFactory.create(gson)

    @Provides
    @Singleton
    fun provideNullOrEmptyConverterFactory(): Converter.Factory =
        object : Converter.Factory() {
            override fun responseBodyConverter(
                type: Type,
                annotations: Array<out Annotation>,
                retrofit: Retrofit
            ): Converter<ResponseBody, Any?> {
                val nextResponseBodyConverter = retrofit.nextResponseBodyConverter<Any?>(
                    this,
                    type,
                    annotations
                )

                return Converter { body: ResponseBody ->
                    if (body.contentLength() == 0L) null
                    else nextResponseBodyConverter.convert(body)
                }
            }
        }


    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        nullOrEmptyConverterFactory: Converter.Factory,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit =
        Retrofit.Builder()
            .client(okHttpClient)
            .addConverterFactory(nullOrEmptyConverterFactory)
            .addConverterFactory(gsonConverterFactory)
            .baseUrl(BASE_URL)
            .build()


}