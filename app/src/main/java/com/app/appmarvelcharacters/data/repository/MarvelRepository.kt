package com.app.appmarvelcharacters.data.repository
import com.app.appmarvelcharacters.data.api.MarvelApi

interface MarvelRepository {
    fun getCharacter(offset: Int, callback: MarvelApi.OnGetMarvelCallback)
    fun getCharacterByName(name: String, offset: Int, callback: MarvelApi.OnGetMarvelCallback)
}