package com.d3st.e_coding.data.foodadditivesrepository.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "food_additives_table")
data class FoodAdditiveDatabaseModel (
    @PrimaryKey(autoGenerate = true)
    val id:Long = 0L,

    @ColumnInfo(name = "canonical_name")
    val canonicalName:String,

    @ColumnInfo(name = "name")
    val name:String,

    @ColumnInfo(name = "alias")
    val alias:List<String>,

    @ColumnInfo(name = "harmfulness")
    val harmfulness:Int,

    @ColumnInfo(name = "is_belong_to_group")
    val isBelongToGroup:Boolean,

    @ColumnInfo(name = "parent_canonical_name")
    val groupParentCanonicalName:String,

    @ColumnInfo(name = "is_allowed_ru")
    val isAllowedRU:Boolean,

    @ColumnInfo(name = "is_allowed_us")
    val isAllowedUS:Boolean,

    @ColumnInfo(name = "is_allowed_eu")
    val isAllowedEU:Boolean,

    @ColumnInfo(name = "functional_class")
    val functionalClass:List<AdditiveType>,

    @ColumnInfo(name = "origin")
    val origin:String,

    @ColumnInfo(name = "description")
    val description:String,

    @ColumnInfo(name = "violated_systems")
    val violatedSystems:List<String>

)


enum class AdditiveType(val value:Int){
    ACIDITY_REGULATOR(1),
    ANTICAKING_AGENT(2),
    ANTIFOAMING_AGENT(3),
    ANTIOXIDANT(4),
    BLEACHING_AGENT(5),
    BULKING_AGENT(6),
    CARBONATING_AGENT(7),
    CARRIER(8),
    COLOUR(9),
    COLOUR_RETENTION_AGENT(10),
    EMULSIFIER(11),
    EMULSIFYING_SALT(12),
    FIRMING_AGENT(13),
    FLAVOUR_ENHANCER(14),
    FLOUR_TREATMENT_AGENT(15),
    FOAMING_AGENT(16),
    GELLING_AGENT(17),
    GLAZING_AGENT(18),
    HUMECTANT(19),
    PACKAGING_GAS(20),
    PRESERVATIVE(21),
    PROPELLANT(22),
    RAISING_AGENT(23),
    SEQUESTRANT(24),
    STABILISER(25),
    SWEETENER(26),
    THICKENER(27);



    companion object {
        private val map = AdditiveType.values().associateBy { it.value }
        infix fun from(value: Int) = map[value]
    }
}
