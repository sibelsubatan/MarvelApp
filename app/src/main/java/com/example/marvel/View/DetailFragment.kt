package com.example.marvel

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.*
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.marvel.Adapter.CharacterComicsAdapter
import com.example.marvel.Model.Model
import com.example.marvel.extensions.load
import com.example.marvel.viewModel.CharacterDetailViewModel
import kotlinx.android.synthetic.main.fragment_detail.*
import kotlinx.android.synthetic.main.fragment_detail.view.*
import kotlinx.android.synthetic.main.fragment_list.view.*
import kotlinx.android.synthetic.main.item_character.*

class DetailFragment : Fragment() {

    private val viewModel: CharacterDetailViewModel by lazy {
        ViewModelProviders.of(this).get(CharacterDetailViewModel::class.java)
    }
    private var comicsList= mutableListOf<Model.Detail>()

    private var id=""

    private val adapter: CharacterComicsAdapter by lazy {
        CharacterComicsAdapter(comicsList)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view=inflater.inflate(R.layout.fragment_detail, container, false)

        val linearLayoutManager = LinearLayoutManager(this.context)
        view.recyclerCharacterComics.layoutManager = linearLayoutManager
        view.recyclerCharacterComics.hasFixedSize()
        view.recyclerCharacterComics.adapter = adapter
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            id=DetailFragmentArgs.fromBundle(it).id.toString()
        }

        subscribeToList(view)
        viewModel.getDetailData(id)
        viewModel.getDetailComicsData(id)
    }

    @SuppressLint("FragmentLiveDataObserve")
    private fun subscribeToList(view:View) {
        viewModel.characterDetail.observe(this@DetailFragment, Observer {
            bindUI(it,view)
        })

        viewModel.characterComicsDetail.observe(this@DetailFragment, Observer {
            adapter.updateList(it)
        })
    }


    fun bindUI( it: Model.Character,view: View){
        val imageUrl = "${it.thumbnail.path}.${it.thumbnail.extension}"
        view.characterName.setText(it.name)
        view.characterDescription.setText(it.description)
        view.characterImage.load(imageUrl)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (activity as? AppCompatActivity)?.supportActionBar?.title = "Detay"
    }
}

