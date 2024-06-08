package com.autthapol.noteappkmm.android.di

import android.app.Application
import app.cash.sqldelight.db.SqlDriver
import com.autthapol.noteappkmm.data.local.DatabaseDriverFactory
import com.autthapol.noteappkmm.data.note.SqlDelightNoteDataSource
import com.autthapol.noteappkmm.database.NoteDatabases
import com.autthapol.noteappkmm.domain.note.NoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideSqlDriver(app: Application): SqlDriver {
        return DatabaseDriverFactory(app).createDriver()
    }

    @Provides
    @Singleton
    fun provideNoteDataSource(driver: SqlDriver): NoteDataSource {
        return SqlDelightNoteDataSource(NoteDatabases(driver))
    }
}