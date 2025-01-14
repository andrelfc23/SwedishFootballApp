package com.abav.footballfranzy.APIFootball

import com.abav.footballfranzy.model.Arena

data class Team(
    val name: String,
    val logo: String,
    val logoResId : Int,
    val arena : Arena
)
