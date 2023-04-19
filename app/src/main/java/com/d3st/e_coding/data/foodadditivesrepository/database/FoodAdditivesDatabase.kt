package com.d3st.e_coding.data.foodadditivesrepository.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [FoodAdditiveDatabaseModel::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class FoodAdditivesDatabase :RoomDatabase() {
    abstract val foodAdditivesDatabaseDao: FoodAdditivesDatabaseDao
    companion object{

        @Volatile
        private var INSTANCE:FoodAdditivesDatabase? = null

        /**
         * This method creates database, will be used later
         */
        fun getInstance(context: Context): FoodAdditivesDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        FoodAdditivesDatabase::class.java,
                        "weather_table"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }

    }
}