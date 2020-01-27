package com.app.appmarvelcharacters.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.app.appmarvelcharacters.R
import com.squareup.picasso.Picasso
import com.app.appmarvelcharacters.models.Result
import kotlinx.android.synthetic.main.item_list_heroes.view.*

class ListHeroesAdapter(private val character: MutableList<Result>,
                        private val listener: AdapterListener
): RecyclerView.Adapter<ListHeroesAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent.inflate(R.layout.item_list_heroes))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(character[position])
    }

    override fun getItemCount() = character.size

    fun updateList(character: MutableList<Result>){
        this.character.clear()
        this.character.addAll(character)
        notifyDataSetChanged()
    }


    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        private lateinit var char: Result

        fun bind(result: Result){
            this.char = result

            Picasso.get()
                .load(result.thumbnail.path + "." + result.thumbnail.extension)
                .into(itemView.image_thumbnail)

                itemView.text_name.text = result.name

            itemView.setOnClickListener{ listener.showDetails(char) }
            itemView.text_name.setOnClickListener{ listener.showDetails(char) }
            itemView.image_thumbnail.setOnClickListener { listener.showDetails(char) }
        }

    }

    interface AdapterListener{
        fun showDetails(char: Result)
    }
}

fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
}