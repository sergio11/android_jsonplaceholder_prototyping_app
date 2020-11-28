package sanchez.sanchez.sergio.postsapp.di.modules.network

import android.content.Context
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.multibindings.ElementsIntoSet
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import sanchez.sanchez.sergio.postsapp.BuildConfig
import sanchez.sanchez.sergio.postsapp.di.scopes.PerApplication
import sanchez.sanchez.sergio.postsapp.persistence.network.interceptors.ConnectivityInterceptor
import java.util.concurrent.TimeUnit
import javax.inject.Named

/**
 * App Network Module
 */
@Module
class NetworkModule {

    /**
     * Provide Converter Factory
     */
    @Provides
    @PerApplication
    fun provideConverterFactory(): Converter.Factory =
        GsonConverterFactory.create(Gson())


    /**
     * Provide Network Interceptors
     */
    @Provides
    @ElementsIntoSet
    @PerApplication
    @Named("networkInterceptors")
    fun provideNetworkInterceptors(): Set<Interceptor> =
        setOf<Interceptor>(StethoInterceptor())

    /**
     * Provide Request Interceptors
     */
    @Provides
    @ElementsIntoSet
    @PerApplication
    @Named("requestInterceptors")
    fun provideRequestInterceptors(
        context: Context): Set<Interceptor> =
        setOf(
            ConnectivityInterceptor(context)
        )

    /**
     * Provide HTTP Client
     * @param networkInterceptors
     * @param requestInterceptors
     */
    @Provides
    @PerApplication
    fun provideHttpClient(
        context: Context,
        @Named("networkInterceptors") networkInterceptors: Set<@JvmSuppressWildcards Interceptor>,
        @Named("requestInterceptors") requestInterceptors: Set<@JvmSuppressWildcards Interceptor>
    ): OkHttpClient {

        val okHttpClientBuilder =  OkHttpClient.Builder()
            .connectTimeout(2, TimeUnit.MINUTES)
            .readTimeout(2, TimeUnit.MINUTES)
            .writeTimeout(2, TimeUnit.MINUTES)
            .retryOnConnectionFailure(true)
            .cache(Cache(context.cacheDir, DEFAULT_CACHE_SIZE))

        networkInterceptors.forEach {
            okHttpClientBuilder.addNetworkInterceptor(it)
        }
        requestInterceptors.forEach {
            okHttpClientBuilder.addInterceptor(it)
        }
        return okHttpClientBuilder.build()
    }

    /**
     * Provide Retrofit
     */
    @Provides
    @PerApplication
    fun provideRetrofit(
        converterFactory: Converter.Factory,
        httpClient: OkHttpClient
    ): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(converterFactory)
            .baseUrl(BuildConfig.BASE_URL)
            .client(httpClient)
            .build()


    companion object {
        const val DEFAULT_CACHE_SIZE: Long =  10 * 1024 * 1024 // 10 MB
    }


}