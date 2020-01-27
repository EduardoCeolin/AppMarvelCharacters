package com.app.appmarvelcharacters.ui.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.appmarvelcharacters.data.api.MarvelApi
import com.app.appmarvelcharacters.models.ReturnData
import com.app.appmarvelcharacters.data.repository.MarvelRepository
import com.app.appmarvelcharacters.data.repository.RepositoryImpl

class ListHeroesViewModel (private val repository: MarvelRepository = RepositoryImpl()) : ViewModel() {
    private val heroesList: MutableLiveData<ReturnData> = MutableLiveData()

    fun getHeroesList() = heroesList

    fun getHeroes(offset: Int) {
        repository.getCharacter(offset, object : MarvelApi.OnGetMarvelCallback {

            override fun onSuccess(marvelResponse: ReturnData) {
                heroesList.value = marvelResponse
            }

            override fun onError() {
            }
        })
    }

    fun getHeroesByName(name: String, offset: Int) {
        repository.getCharacterByName(name, offset, object : MarvelApi.OnGetMarvelCallback {

            override fun onSuccess(marvelResponse: ReturnData) {
                heroesList.value = marvelResponse
            }

            override fun onError() {
            }
        })
    }
}
