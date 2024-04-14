package com.example.pokedex.team.data.db.di

import com.example.pokedex.home.data.db.PokemonDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class PokemonTeamModule {

    @Provides
    fun providesPokemonTeamDao(database: PokemonDatabase) = database.pokemonTeamDao()
}