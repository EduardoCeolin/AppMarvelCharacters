package com.app.appmarvelcharacters.data.api

import com.app.appmarvelcharacters.models.ReturnData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MarvelApi {
        @GET("characters")
        fun getCharacters(@Query("ts") ts: String,
                      @Query("apikey") apiKey: String,
                      @Query("hash") hash: String,
                      @Query("limit") limit: String = "4",
                      @Query("offset") offset: String = "0"): Call<ReturnData>

        @GET("characters")
        fun getCharactersByName(@Query("ts") ts: String,
                  @Query("apikey") apiKey: String,
                  @Query("hash") hash: String,
                  @Query("nameStartsWith") name: String,
                  @Query("limit") limit: String = "4",
                  @Query("offset") offset: String = "0") : Call<ReturnData>

interface OnGetMarvelCallback {
        fun onSuccess(marvelResponse: ReturnData)
        fun onError()
    }
}
