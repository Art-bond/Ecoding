package com.d3st.e_coding.ui.details

import com.d3st.e_coding.data.foodadditivesrepository.database.AdditiveType

data class DetailsFoodAdditiveDomainModel(
    val canonicalName: String,
    val name: String,
    val alias: List<String>,
    val harmfulness: Int,
    val isBelongToGroup: Boolean,
    val groupParentCanonicalName: String,
    val isAllowedRU: Boolean,
    val isAllowedUS: Boolean,
    val isAllowedEU: Boolean,
    val functionalClass: List<AdditiveType>,
    val origin: String,
    val description: String,
    val violatedSystems: List<String>
)