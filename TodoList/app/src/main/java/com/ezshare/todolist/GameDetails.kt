package com.ezshare.todolist

data class GameDetails (
    val id : Int,
    val name : String,
    val picture : String,
    val players : Int,
    val type : String,
    val year : Int,
    val url : String,
    val description_fr : String?,
    val description_en : String)