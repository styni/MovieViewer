package com.styni.movieviewer.di.modules

import android.app.Application
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.styni.movieviewer.di.ForApplication
import com.styni.movieviewer.network.RezkaApi
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import pl.droidsonroids.retrofit2.JspoonConverterFactory
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import ru.spider.spidergrouptest.network.UnsafeOkHttpClient
import javax.inject.Singleton

@Module
class AppModule(private val application: Application) {

    @Provides
    @Singleton
    @ForApplication
    fun provideApplicationContext(): Application = application

    @Provides
    @Singleton
    fun provideHttpClient() = UnsafeOkHttpClient.unsafeOkHttpClient

    @Provides
    @Singleton
    fun provideHtmlApi(httpClient: OkHttpClient): RezkaApi.HtmlApi {
        return Retrofit.Builder()
            .baseUrl(RezkaApi.BACK_URL)
            .client(httpClient)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(JspoonConverterFactory.create())
            .build()
            .create(RezkaApi.HtmlApi::class.java)
    }

    @Provides
    fun provideGson() = GsonBuilder().create()

    @Provides
    fun provideGsonApi(gson: Gson, httpClient: OkHttpClient): RezkaApi.JsonApi {
        return Retrofit.Builder()
            .baseUrl(RezkaApi.BACK_URL)
            .client(httpClient)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(RezkaApi.JsonApi::class.java)
    }
}