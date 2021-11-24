package com.example.marvel.paging

import androidx.paging.DataSource
import com.example.marvel.api.Api
import io.reactivex.disposables.CompositeDisposable
import com.example.marvel.Model.Model.Character

class CharacterDataSourceFactory(
    private val compositeDisposable: CompositeDisposable,
    private val marvelApi: Api
) : DataSource.Factory<Int, Character>() {

    override fun create(): DataSource<Int, Character> {
        return CharactersDataSource(marvelApi, compositeDisposable)
    }
}