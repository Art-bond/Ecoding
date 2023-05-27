package com.d3st.e_coding.di

import android.content.Context
import com.d3st.e_coding.data.foodadditivesrepository.FoodAdditivesRepository
import com.d3st.e_coding.data.foodadditivesrepository.IFoodAdditivesRepository
import com.d3st.e_coding.data.foodadditivesrepository.database.FoodAdditivesDatabase
import com.d3st.e_coding.data.foodadditivesrepository.database.FoodAdditivesDatabaseDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RecognizeModule {

    @Provides
    fun provideFoodAdditivesRepository(
        dao: FoodAdditivesDatabaseDao,
    ): IFoodAdditivesRepository {
        return FoodAdditivesRepository(dao)
    }

    @Provides
    fun provideFoodAdditivesDataBase(
        @ApplicationContext appContext: Context,
    ): FoodAdditivesDatabaseDao {
        return FoodAdditivesDatabase.getInstance(appContext).foodAdditivesDatabaseDao
    }
}