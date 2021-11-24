package com.example.marvel.paging

import android.util.Log
import androidx.paging.PageKeyedDataSource
import com.example.marvel.Model.Model.Character
import com.example.marvel.api.Api
import com.example.marvel.extensions.md5
import com.example.marvel.key.API_KEY
import com.example.marvel.key.PRIVATE_KEY
import io.reactivex.disposables.CompositeDisposable
import java.util.*


class CharactersDataSource(
    private val marvelApi: Api,
    private val compositeDisposable: CompositeDisposable
) : PageKeyedDataSource<Int, Character>() {

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Character>) {
        val numberOfItems = params.requestedLoadSize
        createObservable(0, 1, numberOfItems, callback, null)
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Character>) {
        val page = params.key
        val numberOfItems = params.requestedLoadSize
        createObservable(page, page + 1, numberOfItems, null, callback)
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Character>) {
        val page = params.key
        val numberOfItems = params.requestedLoadSize
        createObservable(page, page - 1, numberOfItems, null, callback)
    }
    val ts = Date().time;
    val hash = (ts.toString()+PRIVATE_KEY+ API_KEY).md5()

    private fun createObservable(
        requestedPage: Int,
        adjacentPage: Int,
        requestedLoadSize: Int,
        initialCallback: LoadInitialCallback<Int, Character>?,
        callback: LoadCallback<Int, Character>?
    ) {
        compositeDisposable.add(
            marvelApi.allCharacters(requestedPage * requestedLoadSize,ts.toString(), API_KEY,hash)
                .subscribe(
                    { response ->
                        Log.d("", "Loading page: $requestedPage")
                        initialCallback?.onResult(response.data.results, null, adjacentPage)
                        callback?.onResult(response.data.results, adjacentPage)
                    },
                    { error ->
                        Log.e("", "error", error)
                    }
                )
        )
    }
}