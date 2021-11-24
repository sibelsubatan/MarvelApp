package com.example.marvel.viewModel

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.marvel.Model.Model
import com.example.marvel.api.Api
import com.example.marvel.extensions.md5
import com.example.marvel.key.API_KEY
import com.example.marvel.key.PRIVATE_KEY
import io.reactivex.schedulers.Schedulers
import java.util.*


class CharacterDetailViewModel:ViewModel() {
    var characterDetail = MutableLiveData<Model.Character>()
    val characterComicsDetail = MutableLiveData<List<Model.Detail>>()

    val marvelApi=Api.create()
    val ts = Date().time;
    val hash = (ts.toString()+ PRIVATE_KEY + API_KEY).md5()

    @SuppressLint("CheckResult")
    fun getDetailData(id: String){
        marvelApi.getCharacterDetail(id,ts.toString(), API_KEY,hash)
                .subscribeOn(Schedulers.io())
                .subscribe(
                        {
                            characterDetail.postValue(it.data.results[0])
                        },
                        {
                            Log.e("characterDetail", it.message.toString())
                        }
                )

    }


    @SuppressLint("CheckResult")
    fun getDetailComicsData(id: String){
        Log.d("id nedir?",id)
        marvelApi.getCharacterComics(id,2005,"onsaleDate",10,ts.toString(), API_KEY,hash)
                .subscribeOn(Schedulers.io())
                .subscribe(
                        {
                            characterComicsDetail.postValue(it.data.results.toList())

                        },
                        {
                            Log.e("charactercomics", it.message.toString())
                        }
                )

    }

}


