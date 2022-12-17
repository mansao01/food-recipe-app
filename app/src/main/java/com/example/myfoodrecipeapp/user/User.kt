package com.example.myfoodrecipeapp.user

data class User(
    var username: String,
    var email: String,
    var password: String,
    var isLogin: Boolean
)