package com.ezshare.todolist

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WsInterface {

    @GET("game/list")
    fun getGameList() : Call<List<GameList>>

    @GET("game/details")
    fun getGameDetails(@Query("game_id") game_id: Int) : Call<GameDetails>

}