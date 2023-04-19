package com.d3st.e_coding.data.foodadditivesrepository

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.d3st.e_coding.data.foodadditivesrepository.database.AdditiveType
import com.d3st.e_coding.data.foodadditivesrepository.database.FoodAdditiveDatabaseModel
import com.d3st.e_coding.data.foodadditivesrepository.database.FoodAdditivesDatabase
import com.d3st.e_coding.data.foodadditivesrepository.database.FoodAdditivesDatabaseDao
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class FoodAdditivesRepositoryTest {

    private lateinit var dao: FoodAdditivesDatabaseDao
    private lateinit var db: FoodAdditivesDatabase
    private lateinit var rep:FoodAdditivesRepository

    @Before
    fun setUp() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        // Using an in-memory database because the information stored here disappears when the
        // process is killed.
        db = Room.inMemoryDatabaseBuilder(context, FoodAdditivesDatabase::class.java)
            // Allowing main thread queries, just for testing.
            .allowMainThreadQueries()
            .build()
        dao = db.foodAdditivesDatabaseDao
        rep = FoodAdditivesRepository(dao)

    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun getAdditiveByCanonicalNameExisting() {
        runBlocking {
            rep.populateDatabase()
            val answer = rep.getAdditiveByCanonicalName("E202")
            assertEquals("Сорбат калия",answer?.name)
        }

    }
    @Test
    fun getAdditiveByCanonicalNameAbsent() {
        runBlocking {
            rep.populateDatabase()
            val answer = rep.getAdditiveByCanonicalName("E1488")
            assertNull(answer)

        }

    }

    @Test
    fun getAdditiveByGroupName() {
    }

    @Test
    fun getAdditivesAliasDictionary() {
        runBlocking {
            dao.insert(
                FoodAdditiveDatabaseModel(
                    canonicalName = "E202",
                    name = "Сорбат калия",
                    alias = listOf("Сорбат калия", "Калия сорбат"),
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
            val answer = mapOf("Сорбат калия" to "E202","Калия сорбат" to "E202")
            assertEquals(answer,rep.getAdditivesAliasDictionary())
        }
    }
}