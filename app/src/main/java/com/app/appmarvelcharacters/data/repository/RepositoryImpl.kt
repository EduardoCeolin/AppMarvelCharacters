package com.app.appmarvelcharacters.data.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.app.appmarvelcharacters.data.api.MarvelApi
import com.app.appmarvelcharacters.models.ReturnData
import com.app.appmarvelcharacters.utils.Constants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.math.BigInteger
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

class RepositoryImpl: MarvelRepository {
    private var service: MarvelApi
    private var heroesData: MutableLiveData<ReturnData> = MutableLiveData()

    companion object {
        const val BASE_URL = "https://gateway.marvel.com/v1/public/"
        const val PUBLIC_KEY = Constants.PUBLIC_KEY
        const val PRIVATE_KEY = Constants.PRIVATE_KEY
    }

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        service = retrofit.create(MarvelApi::class.java)
    }

    private fun getMd5(ts: String): String {
        try {

            val md = MessageDigest.getInstance("MD5")

            val messageDigest = md.digest(ts.toByteArray()
                    + PRIVATE_KEY.toByteArray()
                    + PUBLIC_KEY.toByteArray())

            val no = BigInteger(1, messageDigest)

            var hashtext = no.toString(16)
            while (hashtext.length < 32) {
                hashtext = "0$hashtext"
            }
            return hashtext
        } catch (e: NoSuchAlgorithmException) {
            throw RuntimeException(e)
        }
    }

    override fun getCharacter(offset: Int, callback: MarvelApi.OnGetMarvelCallback) {
        val ts = System.currentTimeMillis().toString()
        var hash = getMd5(ts)

        service.getCharacters(ts, Constants.PUBLIC_KEY, hash, offset = offset.toString())
            .enqueue(object : Callback<ReturnData> {
                override fun onResponse(call: Call<ReturnData>, response: Response<ReturnData>) {

                    if (response.isSuccessful){
                        if (response.body() != null){
                            heroesData.postValue(response.body())
                            callback.onSuccess(response.body()!!)
                        } else {
                            callback.onError()
                            Log.e("Response", " response null")
                        }

                    } else {
                        callback.onError()
                        Log.e("Response", response.raw().networkResponse().toString())
                    }

                }

                override fun onFailure(call: Call<ReturnData>, t: Throwable) {
                    callback.onError()
                    t.printStackTrace()
                    Log.e("Response", javaClass.simpleName + " not response 2 " + t)
                }
            })
    }

    override fun getCharacterByName(name: String, offset: Int, callback: MarvelApi.OnGetMarvelCallback) {
        val ts = System.currentTimeMillis().toString()
        var hash = getMd5(ts)

        service.getCharactersByName(ts, Constants.PUBLIC_KEY, hash, name, offset = offset.toString())
            .enqueue(object : Callback<ReturnData> {
                override fun onResponse(call: Call<ReturnData>, response: Response<ReturnData>) {

                    if (response.isSuccessful){
                        if (response.body() != null){
                            heroesData.postValue(response.body())
                            callback.onSuccess(response.body()!!)
                        } else {
                            callback.onError()
                            Log.e("Response", " response null")
                        }

                    } else {
                        callback.onError()
                        Log.e("Response", response.raw().networkResponse().toString())
                    }

                }

                override fun onFailure(call: Call<ReturnData>, t: Throwable) {
                    callback.onError()
                    t.printStackTrace()
                    Log.e("Response", javaClass.simpleName + " not response 2 " + t)
                }
            })
    }
}