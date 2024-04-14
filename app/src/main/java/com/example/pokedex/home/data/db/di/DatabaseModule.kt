package com.example.pokedex.home.data.db.di

import android.content.Context
import androidx.room.Room
import com.example.pokedex.home.data.db.PokemonDao
import com.example.pokedex.home.data.db.PokemonDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    fun providesPokemonDao(pokemonDatabase: PokemonDatabase): PokemonDao {
        return pokemonDatabase.pokemonDao()
    }

    @Provides
    @Singleton
    fun providesPokemonDatabase(@ApplicationContext appContext: Context): PokemonDatabase {
        return Room.databaseBuilder(appContext, PokemonDatabase::class.java, "PokemonDatabase")
            .build()
    }

}