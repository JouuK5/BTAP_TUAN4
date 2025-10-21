package com.example.forgotpassword

import kotlinx.serialization.Serializable

@Serializable data object Home
@Serializable data class Verify(val email:String)
@Serializable data class NewPass(val email:String, val code:String)
@Serializable data class Confirm(val email: String, val code: String, val password: String)



