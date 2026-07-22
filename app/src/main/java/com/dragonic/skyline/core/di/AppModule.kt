package com.dragonic.skyline.core.di

import android.content.Context
import androidx.room.Room
import com.dragonic.skyline.data.local.SkylineDatabase
import com.dragonic.skyline.data.local.dao.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
import javax.inject.Singleton

// ── QUALIFIER ANNOTATIONS ────────────────────────────────────

@Qualifier @Retention(AnnotationRetention.BINARY) annotation class IoDispatcher
@Qualifier @Retention(AnnotationRetention.BINARY) annotation class MainDispatcher
@Qualifier @Retention(AnnotationRetention.BINARY) annotation class DefaultDispatcher

// ── DATABASE MODULE ──────────────────────────────────────────

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides @Singleton
    fun provideDatabase(@ApplicationContext context: Context): SkylineDatabase =
        Room.databaseBuilder(context, SkylineDatabase::class.java, SkylineDatabase.DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()

    @Provides fun provideUserDao(db: SkylineDatabase): UserDao = db.userDao()
    @Provides fun provideDeviceDao(db: SkylineDatabase): DeviceDao = db.deviceDao()
    @Provides fun provideIoTDeviceDao(db: SkylineDatabase): IoTDeviceDao = db.ioTDeviceDao()
    @Provides fun provideActivityLogDao(db: SkylineDatabase): ActivityLogDao = db.activityLogDao()
    @Provides fun provideAppSettingsDao(db: SkylineDatabase): AppSettingsDao = db.appSettingsDao()
}

// ── NETWORK MODULE ───────────────────────────────────────────

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides @Singleton
    fun provideOkHttpClient(): OkHttpClient =
        OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()
}

// ── COROUTINE DISPATCHER MODULE ──────────────────────────────

@Module
@InstallIn(SingletonComponent::class)
object DispatcherModule {

    @Provides @IoDispatcher
    fun provideIoDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Provides @MainDispatcher
    fun provideMainDispatcher(): CoroutineDispatcher = Dispatchers.Main

    @Provides @DefaultDispatcher
    fun provideDefaultDispatcher(): CoroutineDispatcher = Dispatchers.Default
}
