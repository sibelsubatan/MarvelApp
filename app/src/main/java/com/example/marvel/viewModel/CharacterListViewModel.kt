package com.example.marvel.viewModel

import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import androidx.paging.RxPagedListBuilder
import com.example.marvel.api.Api
import com.example.marvel.paging.CharacterDataSourceFactory
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import com.example.marvel.Model.Model.Character

class CharacterListViewModel: ViewModel() {

    var characterList: Observable<PagedList<Character>>

    private val compositeDisposable = CompositeDisposable()

    private val pagedSize = 30

    private val sourceFactory: CharacterDataSourceFactory

    init {
        sourceFactory = CharacterDataSourceFactory(
            compositeDisposable,
            Api.create()
        )

        val config = PagedList.Config.Builder()
            .setPageSize(pagedSize)
            .setInitialLoadSizeHint(pagedSize * 3)
            .setPrefetchDistance(10)
            .setEnablePlaceholders(false)
            .build()

        characterList = RxPagedListBuilder(sourceFactory, config)
            .setFetchScheduler(Schedulers.io())
            .buildObservable()
            .cache()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}