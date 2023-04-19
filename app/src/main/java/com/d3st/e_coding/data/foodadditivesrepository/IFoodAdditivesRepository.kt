package com.d3st.e_coding.data.foodadditivesrepository

import com.d3st.e_coding.data.foodadditivesrepository.database.FoodAdditiveDatabaseModel

interface IFoodAdditivesRepository {

    suspend fun getAdditiveByCanonicalName(name:String): FoodAdditiveDatabaseModel?

    suspend fun getAdditiveByGroupName(groupName:String): List<FoodAdditiveDatabaseModel>

    suspend fun getAdditivesAliasDictionary(): Map<String,String>

    suspend fun populateDatabase()
}