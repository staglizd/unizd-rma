package org.unizd.rma.putopis.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import org.unizd.rma.putopis.data.repository.CityRepositoryImpl
import org.unizd.rma.putopis.domain.repository.CityRepository

/** "Kad netko traži sučelje CityRepository, daj mu CityRepositoryImpl." */
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindCityRepository(impl: CityRepositoryImpl): CityRepository
}
