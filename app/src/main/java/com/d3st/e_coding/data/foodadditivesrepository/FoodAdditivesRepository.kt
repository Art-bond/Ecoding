package com.d3st.e_coding.data.foodadditivesrepository

import com.d3st.e_coding.data.foodadditivesrepository.database.AdditiveType
import com.d3st.e_coding.data.foodadditivesrepository.database.FoodAdditiveDatabaseModel
import com.d3st.e_coding.data.foodadditivesrepository.database.FoodAdditivesDatabaseDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FoodAdditivesRepository(private val dao: FoodAdditivesDatabaseDao) :
    IFoodAdditivesRepository {

    override suspend fun populateDatabase() {
        if (dao.getCount() == 0) {
            withContext(Dispatchers.IO) {
                dao.insert(
                    FoodAdditiveDatabaseModel(
                        canonicalName = "E202",
                        name = "Сорбат калия",
                        alias = listOf("Сорбат* калия", "Калия сорбат*","Е202","Е 202","Е-202"),
                        harmfulness = 1,
                        isBelongToGroup = false,
                        groupParentCanonicalName = "",
                        isAllowedRU = true,
                        isAllowedUS = true,
                        isAllowedEU = true,
                        functionalClass = listOf(AdditiveType.PRESERVATIVE),
                        origin = "artificial",
                        description = "Common preservative",
                        violatedSystems = emptyList()
                    )
                )
                dao.insert(
                    FoodAdditiveDatabaseModel(
                        canonicalName = "E211",
                        name = "Бензоат натрия",
                        alias = listOf("Бензоат* натрия", "Натрия бензоат*","Е211","Е 211","Е-211"),
                        harmfulness = 4,
                        isBelongToGroup = false,
                        groupParentCanonicalName = "",
                        isAllowedRU = true,
                        isAllowedUS = true,
                        isAllowedEU = true,
                        functionalClass = listOf(AdditiveType.PRESERVATIVE),
                        origin = "synthetic",
                        description = "Common preservative",
                        violatedSystems = listOf("cancerogen")
                    )
                )
                dao.insert(
                    FoodAdditiveDatabaseModel(
                        canonicalName = "E160",
                        name = "Каротиноиды",
                        alias = listOf("Каротиноид*"),
                        harmfulness = 1,
                        isBelongToGroup = false,
                        groupParentCanonicalName = "",
                        isAllowedRU = true,
                        isAllowedUS = true,
                        isAllowedEU = true,
                        functionalClass = listOf(AdditiveType.COLOUR),
                        origin = "floral",
                        description = "Natural colouring, from variety of different sources",
                        violatedSystems = emptyList()
                    )
                )
                dao.insert(
                    FoodAdditiveDatabaseModel(
                        canonicalName = "E160c",
                        name = "Маслосмолы паприки",
                        alias = listOf("Маслосмолы паприки","Экстракт* паприки","Паприки экстракт*"),
                        harmfulness = 0,
                        isBelongToGroup = true,
                        groupParentCanonicalName = "E160",
                        isAllowedRU = true,
                        isAllowedUS = true,
                        isAllowedEU = true,
                        functionalClass = listOf(AdditiveType.COLOUR),
                        origin = "floral",
                        description = "Natural colouring, from paprika pepper",
                        violatedSystems = emptyList()
                    )
                )
            }
        }
    }

    override suspend fun getAdditiveByCanonicalName(name: String): FoodAdditiveDatabaseModel? {
//        populateDatabase()
        return dao.getByCanonicalName(name)
    }

    override suspend fun getAdditiveByGroupName(groupName: String): List<FoodAdditiveDatabaseModel> {
//        populateDatabase()
        return dao.getByGroupName(groupName)
    }

    override suspend fun getAdditivesAliasDictionary(): Map<String,String> {
//        populateDatabase()
        val dictionary = mutableMapOf<String,String>()
        val allEntries = dao.getAll()
        for (entrie in allEntries){
            for(alias in entrie.alias){
                dictionary.put(alias,entrie.canonicalName)
            }
        }
        return  dictionary.toMap()
    }

}