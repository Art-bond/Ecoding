package com.d3st.e_coding.ui.start

/**
 * State of start navigation
 */
sealed class StartNavigationState {

    object Start : StartNavigationState()
    object Recognize : StartNavigationState()
    object Compendium : StartNavigationState()
}