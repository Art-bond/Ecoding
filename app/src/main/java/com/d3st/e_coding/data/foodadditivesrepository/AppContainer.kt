package com.d3st.e_coding.data.foodadditivesrepository

import android.content.Context
import com.d3st.e_coding.data.foodadditivesrepository.database.FoodAdditivesDatabase

interface AppContainer {
    val foodAdditivesRepository: FoodAdditivesRepository
}

class AppDataContainer(private val context: Context) : AppContainer {
    override val foodAdditivesRepository: FoodAdditivesRepository by lazy {
        FoodAdditivesRepository(FoodAdditivesDatabase.getInstance(context).foodAdditivesDatabaseDao)
    }
}