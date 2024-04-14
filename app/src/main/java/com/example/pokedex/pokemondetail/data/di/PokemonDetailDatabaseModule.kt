package com.example.pokedex.pokemondetail.data.di

import com.example.pokedex.home.data.db.PokemonDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class PokemonDetailDatabaseModule {

    @Provides
    @Singleton
    fun providesPokemonDetailDao(pokemonDatabase: PokemonDatabase) =
        pokemonDatabase.pokemonDetailDao()
}