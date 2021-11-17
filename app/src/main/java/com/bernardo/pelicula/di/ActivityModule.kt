package com.bernardo.pelicula.di

import com.bernardo.pelicula.data.DataSourceImpl
import com.bernardo.pelicula.domain.DataSource
import com.bernardo.pelicula.domain.Repo
import com.bernardo.pelicula.domain.RepoImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class ActivityModule {

    @Binds
    abstract  fun bindRepoImpl(repoImpl: RepoImpl) : Repo

    @Binds
    abstract  fun bindDataSourceImpl(dataSourceImpl: DataSourceImpl) : DataSource
}