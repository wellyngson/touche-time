package br.touchetime.di

import br.touchetime.data.repository.AthleteRepository
import br.touchetime.data.repository.AthleteRepositoryImpl
import br.touchetime.data.repository.ScoreRepository
import br.touchetime.data.repository.ScoreRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Singleton
    @Binds
    abstract fun bindScoreRepository(
        scoreRepository: ScoreRepositoryImpl,
    ): ScoreRepository

    @Singleton
    @Binds
    abstract fun bindAthleteRepository(
        athleteRepository: AthleteRepositoryImpl,
    ): AthleteRepository
}
