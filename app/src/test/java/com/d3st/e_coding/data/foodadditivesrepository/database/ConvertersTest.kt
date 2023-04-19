package com.d3st.e_coding.data.foodadditivesrepository.database

import org.junit.Assert.assertEquals
import org.junit.Test


internal class ConvertersTest {


    @Test
    fun additiveTypeListToString_TwoItemsTest() {
        val input = listOf(AdditiveType.ACIDITY_REGULATOR,AdditiveType.ANTICAKING_AGENT)
        val conv = Converters()
        assertEquals("1@2",conv.additiveTypeListToString(input))
    }
    @Test
    fun additiveTypeListToString_SingleItemTest() {
        val input = listOf(AdditiveType.ACIDITY_REGULATOR)
        val conv = Converters()
        assertEquals("1",conv.additiveTypeListToString(input))
    }
    @Test
    fun additiveTypeListToString_NoItemsTest() {
        val input = emptyList<AdditiveType>()
        val conv = Converters()
        assertEquals("",conv.additiveTypeListToString(input))
    }
    @Test
    fun stringToAdditiveTypeList_TwoItemsTest() {
        val inputList = listOf(AdditiveType.ACIDITY_REGULATOR,AdditiveType.ANTICAKING_AGENT)
        val input = "1@2"
        val conv = Converters()
        assertEquals(inputList,conv.stringToAdditiveTypeList(input))
    }
    @Test
    fun stringToListOfStrings_TwoItemsTest(){
        val input = "first@second"
        val conv = Converters()
        assertEquals(listOf("first","second"),conv.stringToList(input))
    }

    @Test
    fun listToString_TwoItemsTest(){
        val input = listOf("first","second")
        val conv = Converters()
        assertEquals("first@second",conv.listToString(input))
    }
}