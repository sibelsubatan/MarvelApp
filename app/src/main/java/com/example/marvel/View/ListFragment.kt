package com.example.marvel.View

import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.marvel.Adapter.CharacterAdapter
import com.example.marvel.Model.Model
import com.example.marvel.R
import com.example.marvel.viewModel.CharacterListViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.fragment_list.view.*

class ListFragment : Fragment() {
    private var characterList= mutableListOf<Model.Character>()
    private val characterListViewModel: CharacterListViewModel by lazy {
        ViewModelProviders.of(this).get(CharacterListViewModel::class.java)
    }

    private val adapter: CharacterAdapter by lazy {
        CharacterAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    private var recyclerState: Parcelable? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view=inflater.inflate(R.layout.fragment_list, container, false)
        initRecycler(view)

        return view
    }

    private fun initRecycler(view:View) {
        val linearLayoutManager = LinearLayoutManager(this.context)
        view.recyclerCharacters.layoutManager = linearLayoutManager
        view.recyclerCharacters.hasFixedSize()
        view.recyclerCharacters.adapter = adapter
        subscribeToList(view)
    }
    private fun subscribeToList(view:View) {
        val disposable = characterListViewModel.characterList
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { list ->
                    characterList = list.toMutableList()
                    adapter.submitList(list)
                    if (recyclerState != null) {
                        view.recyclerCharacters.layoutManager?.onRestoreInstanceState(recyclerState)
                        recyclerState = null

                    }
                },
                { e ->
                    Log.e("ListFragment", "Error", e)
                }
            )
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (activity as? AppCompatActivity)?.supportActionBar?.title = "MARVEL"
    }

}