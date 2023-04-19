package com.d3st.e_coding.data.foodadditivesrepository.database

import androidx.room.TypeConverter

class Converters{
    @TypeConverter
    fun stringToList(input:String): List<String>{
        return input.split("@")

    }
    @TypeConverter
    fun listToString(input:List<String>) : String{
        return input.joinToString(separator = "@")
    }

    @TypeConverter
    fun stringToAdditiveTypeList(input:String): List<AdditiveType>{
        return input.split("@").map{ AdditiveType.from(it.toInt())!!}
    }
    @TypeConverter
    fun additiveTypeListToString(input: List<AdditiveType>): String{
//        var str:String = ""
//        for(item in input){
//            if(str.length==0){
//                str+=item.value
//            }
//            else{
//                str+="@"
//                str+=item.value
//            }
//        }
//        return str
        return input.joinToString(separator="@") { t -> t.value.toString() }
    }
}