package fer.digobr.kidslingo.di

import com.squareup.moshi.JsonDataException
import com.squareup.moshi.JsonReader
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import fer.digobr.kidslingo.data.rest.KidsLingoApi
import fer.digobr.kidslingo.data.rest.OpenAiApi
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okio.Buffer
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import timber.log.Timber
import java.sql.Timestamp
import java.util.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideKidsLingoApiService(retrofit: Retrofit): KidsLingoApi {
        return retrofit.create(KidsLingoApi::class.java)
    }

    @Provides
    @Singleton
    fun provideOpenAiApiService(retrofit: Retrofit): OpenAiApi {
        return retrofit.create(OpenAiApi::class.java)
    }

    @Provides
    fun provideRetrofit(moshi: Moshi, okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(okHttpClient)
            .baseUrl("http://10.0.2.2:8080")
            .build()
    }

    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient()
            .newBuilder()
            .build()
    }

    @Provides
    fun provideMoshi(): Moshi =
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .add(Timestamp::class.java, Rfc3339DateJsonAdapter().nullSafe())
            .add(Date::class.java, Rfc3339DateJsonAdapter().nullSafe())
            .build()

    private fun loggingInterceptor(): Interceptor {
        val logTag = "OkHttp"
        return HttpLoggingInterceptor { message ->
            if (message.startsWith("{") || message.startsWith("[")) {
                try {
                    val prettyJson = formatMessageIntoPrettyJson(message)
                    Timber.tag(logTag).v(prettyJson)
                } catch (exception: JsonDataException) {
                    Timber.tag(logTag).e(exception)
                }
            } else {
                Timber.tag(logTag).d(message)
            }
        }.apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    private fun formatMessageIntoPrettyJson(message: String): String {
        val bufferedMessage = Buffer().writeUtf8(message)
        val reader = JsonReader.of(bufferedMessage)
        val uglyJson = reader.readJsonValue()
        val adapter = Moshi.Builder()
            .build()
            .adapter(Any::class.java)
            .indent("    ")
        return adapter.toJson(uglyJson)
    }
}
