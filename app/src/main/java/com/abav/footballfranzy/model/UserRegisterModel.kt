package com.abav.footballfranzy.model

data class UserRegisterModel(
    // unique id for each user
    val uId: String? = "",
    // store users name
    val name: String? = "",
    val phone: String? = "",
    val email: String? = "",
    val password: String? = "",
)
