package com.d3st.e_coding

import android.app.Application
import com.d3st.e_coding.data.foodadditivesrepository.AppContainer
import com.d3st.e_coding.data.foodadditivesrepository.AppDataContainer

class EcodingApplication : Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)

//        GlobalScope.launch {
////                container.foodAdditivesRepository.populateDatabase()
////                container.foodAdditivesRepository.countEntries()
//            container.foodAdditivesRepository.clearDatabase()
//        }

    }
}