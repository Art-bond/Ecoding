package com.d3st.e_coding.data.foodadditivesrepository

import com.d3st.e_coding.data.foodadditivesrepository.database.FoodAdditiveDatabaseModel
import com.d3st.e_coding.ui.details.DetailsFoodAdditiveDomainModel

interface IFoodAdditivesRepository {
    /**
     * Search database for given canonical name
     * @param name canonical name as string
     * @return If object with given canonical name is found in database, return [FoodAdditiveDatabaseModel], otherwise null
     */
    suspend fun getAdditiveByCanonicalName(name: String): FoodAdditiveDatabaseModel?

    suspend fun getAdditiveByGroupName(groupName: String): List<FoodAdditiveDatabaseModel>

    suspend fun getAdditivesAliasDictionary(): Map<String, String>

    suspend fun getAll(): List<DetailsFoodAdditiveDomainModel>
//    suspend fun populateDatabase()

    /**
     * Get List [FoodAdditiveDatabaseModel] by list of additive's name
     *
     * @return if additives has been found, it is added to the list [FoodAdditiveDatabaseModel]
     */
    suspend fun getAdditivesByNames(names: List<String>): List<FoodAdditiveDatabaseModel>
}