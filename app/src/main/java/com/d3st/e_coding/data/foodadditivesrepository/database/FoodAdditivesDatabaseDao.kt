package com.d3st.e_coding.data.foodadditivesrepository.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface FoodAdditivesDatabaseDao {

    @Insert
    fun insert(additive: FoodAdditiveDatabaseModel)

    @Update
    fun update(additive: FoodAdditiveDatabaseModel)

    @Insert
    fun insertAll(additives: List<FoodAdditiveDatabaseModel>)

    @Query("SELECT * FROM food_additives_table WHERE canonical_name = :canonicalName")
    fun getByCanonicalName(canonicalName:String): FoodAdditiveDatabaseModel?

    @Query("SELECT * FROM food_additives_table WHERE parent_canonical_name = :groupName")
    fun getByGroupName(groupName:String): List<FoodAdditiveDatabaseModel>

    @Query("SELECT * FROM food_additives_table ORDER BY id ASC")
    fun getAll():List<FoodAdditiveDatabaseModel>

    @Query("DELETE FROM food_additives_table")
    fun clearAll()

    @Query("SELECT COUNT(*) FROM food_additives_table")
    fun getCount(): Int
}