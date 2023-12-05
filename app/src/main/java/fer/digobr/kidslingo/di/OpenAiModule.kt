package fer.digobr.kidslingo.di

import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import fer.digobr.kidslingo.data.rest.OpenAiApi
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object OpenAiApiModule {

    @Provides
    @Singleton
    fun provideOpenAiApiService(@OpenAiBaseUrl retrofit: Retrofit): OpenAiApi {
        return retrofit.create(OpenAiApi::class.java)
    }

    @Provides
    @OpenAiBaseUrl
    @Singleton
    fun provideRetrofit(
        moshi: Moshi,
        @OpenAiOkHttp okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(okHttpClient)
            .baseUrl("https://api.openai.com/v1/")
            .build()
    }

    @Provides
    @OpenAiOkHttp
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(120, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(
                Interceptor {
                    val apiKey = "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"

                    val newRequest = it.request().newBuilder()
                        .addHeader("Authorization", "Bearer $apiKey")
                        .build()

                    it.proceed(newRequest)
                },
            )
            .build()
    }
}


