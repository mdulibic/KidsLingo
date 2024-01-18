package fer.digobr.kidslingo.di

import com.squareup.moshi.JsonDataException
import com.squareup.moshi.JsonReader
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import fer.digobr.kidslingo.data.GameRepository
import fer.digobr.kidslingo.data.rest.KidsLingoApi
import fer.digobr.kidslingo.data.rest.OpenAiApi
import fer.digobr.kidslingo.domain.GameRepositoryImpl
import fer.digobr.kidslingo.domain.mapper.GameMapper
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okio.Buffer
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import timber.log.Timber
import java.util.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object KidsLingoModule {

    @Provides
    @Singleton
    fun provideKidsLingoApiService(@KidsLingoBaseUrl retrofit: Retrofit): KidsLingoApi {
        return retrofit.create(KidsLingoApi::class.java)
    }

    @Provides
    @KidsLingoBaseUrl
    @Singleton
    fun provideRetrofit(moshi: Moshi, @KidsLingoOkHttp okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(okHttpClient)
            .baseUrl("http://172.20.10.11:8080")
            .build()
    }

    @Provides
    @KidsLingoOkHttp
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient()
            .newBuilder()
            .addInterceptor(interceptor = loggingInterceptor())
            .build()
    }

    @Provides
    @Singleton
    fun provideGameRepository(
        openAiApi: OpenAiApi,
        api: KidsLingoApi,
        mapper: GameMapper
    ): GameRepository =
        GameRepositoryImpl(api = api, openAiApi = openAiApi, mapper = mapper)

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
