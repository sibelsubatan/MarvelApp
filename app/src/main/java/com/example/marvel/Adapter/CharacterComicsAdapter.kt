package com.example.marvel.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.marvel.Model.Model
import com.example.marvel.R
import com.example.marvel.extensions.load
import kotlinx.android.synthetic.main.item_comics.view.*

class CharacterComicsAdapter(var comicsList: List<Model.Detail>) : RecyclerView.Adapter<CharacterComicsAdapter.comicsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): comicsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_comics, parent, false)

        return comicsViewHolder(view)

    }
    class comicsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
         val txtTitle: TextView = itemView.txtTitle
         val imgComicsThumbnail: ImageView = itemView.imgComicsThumbnail
        fun bind(comicsListModel: Model.Detail) {
            comicsListModel.apply {
                txtTitle.text = title
                imgComicsThumbnail.load("${this.thumbnail.path}.${this.thumbnail.extension}")
            }

        }

    }

    override fun getItemId(position: Int): Long {
        return super.getItemId(position)
    }



    override fun onBindViewHolder(holder: comicsViewHolder, position: Int) {
        holder.bind(comicsList[position])
    }

    override fun getItemCount(): Int {
        return comicsList.size
    }

    fun updateList(comicsList: List<Model.Detail>) {
        this.comicsList = comicsList
        this.notifyDataSetChanged()
    }

}
