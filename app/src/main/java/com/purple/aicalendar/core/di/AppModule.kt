package com.purple.aicalendar.core.di

import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.purple.aicalendar.BuildConfig
import com.purple.aicalendar.core.utils.AICalendarPreference
import com.purple.aicalendar.data.api.AICalenderApiServices
import com.purple.aicalendar.data.dao.EventDao
import com.purple.aicalendar.data.db.CalenderDatabase
import com.purple.aicalendar.data.repository.CalendarRepositoryImpl
import com.purple.aicalendar.data.repository.EventsRepositoryImpl
import com.purple.aicalendar.data.repository.TaskRepositoryImpl
import com.purple.aicalendar.domain.repository.CalendarRepository
import com.purple.aicalendar.domain.repository.EventsRepository
import com.purple.aicalendar.domain.repository.TaskRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * Dagger Hilt module that provides application-level dependencies.
 *
 * This module is installed in the [SingletonComponent], meaning that any
 * bindings it provides are available as singletons throughout the application's
 * lifecycle. It is responsible for creating and providing instances of network
 * services, the Room database, DAOs, and repositories.
 */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    /**
     * Provides a singleton instance of [OkHttpClient].
     *
     * This client is configured with a logging interceptor to log the body of HTTP requests
     * and responses, which is useful for debugging. It also sets connection, read, and write
     * timeouts to 30 seconds to prevent long-running requests from hanging.
     *
     * @return A configured [OkHttpClient] instance.
     */
    @Provides
    @Singleton
    fun provideOkhttpClient(): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()

    /**
     * Provides a singleton instance of [Retrofit].
     *
     * This function creates and configures a Retrofit instance for handling
     * network requests. It sets the base URL from the build configuration and
     * uses a custom [OkHttpClient] for underlying HTTP communication.
     * It also adds a [GsonConverterFactory] to handle JSON serialization and
     * deserialization.
     *
     * @param okHttpClient The [OkHttpClient] instance to be used by Retrofit.
     * @return A configured, singleton [Retrofit] instance.
     */
    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    /**
     * Provides a singleton instance of [AICalenderApiServices].
     *
     * This function creates the Retrofit service interface for the Dragon API. It uses the
     * provided [Retrofit] instance to generate the implementation for [AICalenderApiServices].
     * This service is then used by repositories to make network requests to the backend.
     *
     * @param retrofit The configured [Retrofit] instance.
     * @return A singleton instance of [AICalenderApiServices].
     */
    @Provides
    @Singleton
    fun provideAICalenderApiService(retrofit: Retrofit): AICalenderApiServices = retrofit
        .create(AICalenderApiServices::class.java)

    /**
     * Provides a singleton instance of [AICalendarPreference].
     *
     * This function creates and provides a singleton instance of [AICalendarPreference],
     * which is responsible for managing shared preferences for the AI Calendar application.
     * It takes the application [Context] as a dependency to access the underlying
     * SharedPreferences storage. This is typically used for storing user settings,
     * authentication tokens, or other simple key-value data.
     *
     * @param context The application context, provided by Hilt via `@ApplicationContext`.
     * @return A singleton instance of [AICalendarPreference].
     */
    @Provides
    @Singleton
    fun provideAICalendarPreference(
        @ApplicationContext context: Context
    ): AICalendarPreference = AICalendarPreference(context)


    // Migration from version 1 to 2
    private val MIGRATION_1_2 = object : Migration(1, 2) {
        override fun migrate(db: SupportSQLiteDatabase) {
            db.execSQL(
                "ALTER TABLE event_model ADD COLUMN pendingDelete INTEGER NOT NULL DEFAULT 0"
            )
        }
    }
    /**
     * Provides a singleton instance of the [CalenderDatabase].
     *
     * This function is responsible for building the Room database instance for the application.
     * It uses the application context to create the database named "user_database".
     * Destructive migration is disabled, meaning database schema changes will require a proper
     * migration strategy to avoid data loss.
     *
     * @param application The application [Context] provided by Hilt.
     * @return An instance of [CalenderDatabase].
     */
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext application: Context): CalenderDatabase {
        return Room.databaseBuilder(
            application,
            CalenderDatabase::class.java,
            "user_database"
        )
            .addMigrations(MIGRATION_1_2) // <-- add your migration here
            .fallbackToDestructiveMigration(false) // optional, keeps existing data
            .build()


    }
    /**
     * Provides the Data Access Object (DAO) for user notes.
     *
     * @param database The [CalenderDatabase] instance from which to get the DAO.
     * @return An instance of [EventDao] for database operations related to notes.
     */
    @Provides
    @Singleton
    fun provideEventDao(database: CalenderDatabase) = database.eventDao()

    /**
     * Provides a singleton instance of the [TaskRepository].
     *
     * This function is used by Hilt to inject the task repository dependency
     * where needed. It creates an instance of [TaskRepositoryImpl], which is the
     * concrete implementation of the [TaskRepository] interface.
     *
     * @return An instance of [TaskRepository].
     */
    @Provides
    @Singleton
    fun provideTaskRepository(
        aiCalendarPreference: AICalendarPreference,
        eventDao: EventDao,
        apiService: AICalenderApiServices
    ): TaskRepository = TaskRepositoryImpl(aiCalendarPreference,eventDao,apiService)

    /**
     * Provides a singleton instance of the [EventsRepository].
     *
     * This function creates the implementation for the [EventsRepository] interface,
     * which is responsible for managing event data. It depends on [EventDao] for local
     * database operations and [AICalenderApiServices] for remote API calls.
     * This setup allows the repository to abstract away the data sources (local vs. remote)
     * from the rest of the application.
     *
     * @param eventDao The Data Access Object for events, used for local data storage.
     * @return An implementation of [EventsRepository].
     */
    @Provides
    @Singleton
    fun provideEventRepository(eventDao: EventDao,apiService: AICalenderApiServices): EventsRepository = EventsRepositoryImpl(eventDao,apiService)

    /**
     * Provides a singleton instance of [CalendarRepository].
     *
     * This function is used by Hilt to inject the calendar repository dependency
     * where needed. It returns a concrete implementation, [CalendarRepositoryImpl],
     * which handles calendar-related data operations.
     *
     * @return An instance of [CalendarRepository].
     */
    @Provides
    @Singleton
    fun provideCalenderRepository(eventDao: EventDao,apiService: AICalenderApiServices): CalendarRepository = CalendarRepositoryImpl(eventDao,apiService)
}