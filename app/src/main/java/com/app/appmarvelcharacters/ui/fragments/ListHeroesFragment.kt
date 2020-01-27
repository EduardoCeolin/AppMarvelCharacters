package com.app.appmarvelcharacters.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.appmarvelcharacters.R
import com.app.appmarvelcharacters.ui.adapters.ListHeroesAdapter
import com.app.appmarvelcharacters.models.*
import com.app.appmarvelcharacters.ui.activities.DetailsCharacterActivity
import com.app.appmarvelcharacters.ui.viewModel.ListHeroesViewModel
import kotlinx.android.synthetic.main.list_heroes_fragment.*

class ListHeroesFragment : Fragment(),
    ListHeroesAdapter.AdapterListener {

    companion object {
        fun newInstance() = ListHeroesFragment()
    }

    private var viewModel: ListHeroesViewModel =
        ListHeroesViewModel()
    private lateinit var adapter: ListHeroesAdapter

    private var offset: Int = 0
    private var page: Int = 1
    private var offsetFind: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.list_heroes_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        adapter = ListHeroesAdapter(
            mutableListOf(),
            this
        )
        viewModel = ViewModelProviders.of(this).get(ListHeroesViewModel::class.java)

        research.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(research.length() > 3){
                    showLoading()
                    viewModel.getHeroesByName(research.text.toString(), offsetFind)
                    page=1
                    currentPage.text=page.toString()
                    hideLoading()

                    nextPageButton.setOnClickListener{
                        showLoading()
                        offsetFind+=4
                        viewModel.getHeroesByName(research.text.toString(), offsetFind)
                        page+=1
                        currentPage.text=page.toString()
                        hideLoading()
                        }

                    backPageButton.setOnClickListener{
                        showLoading()
                        if(offsetFind==0){
                            viewModel.getHeroesByName(research.text.toString(), offsetFind)
                            hideLoading()
                        }else{
                            offsetFind-=4
                            viewModel.getHeroesByName(research.text.toString(), offsetFind )
                            page-=1
                            currentPage.text=page.toString()
                            hideLoading()
                        }
                    }
                }
                else{
                    offsetFind = 0
                    page = if(offset!=0){
                        (offset/4)+1
                    } else{
                        1
                    }
                    currentPage.text=page.toString()
                    viewModel.getHeroes(offset)

                    setNextAndBackPage()
                }
            }
        })

        recycler_view_characters.layoutManager = LinearLayoutManager(context)
        recycler_view_characters.itemAnimator = DefaultItemAnimator()
        recycler_view_characters.adapter = adapter

        viewModel.getHeroes(offset)
        setNextAndBackPage()
        configureObservers()
    }

    fun configureObservers(){
        showLoading()

        viewModel.getHeroesList().observe(this, Observer { heroes ->
            heroes?.let {
                adapter.updateList(heroes.data.results as MutableList<Result>)
            }
        })

        hideLoading()
    }

    fun showLoading(){
        progressbar_home.visibility = View.VISIBLE
    }

    fun hideLoading(){
        progressbar_home.visibility = View.GONE
    }

    fun setNextAndBackPage(){
        nextPageButton.setOnClickListener{
            showLoading()
            offset+=4
            viewModel.getHeroes(offset)
            page+=1
            currentPage.text=page.toString()
            hideLoading()
        }

        backPageButton.setOnClickListener{
            showLoading()
            if(offset==0){
                viewModel.getHeroes(offset)
            }else {
                offset-=4
                viewModel.getHeroes(offset)
                page-=1
                currentPage.text=page.toString()
            }
            hideLoading()
        }
    }
    override fun showDetails(char: Result) {
        val intent = Intent(activity, DetailsCharacterActivity::class.java)
        intent.putExtra("image", char.thumbnail.path + "." + char.thumbnail.extension)
        intent.putExtra("name", char.name)
        intent.putExtra("description", char.description)
        intent.putExtra("appearances", char.comics.items.size.toString())
        var appearancesList = ""
        char.comics.items.forEach {
            appearancesList = appearancesList + it.name + "\n"
        }
        intent.putExtra("appearancesList", appearancesList)

        var series = ""
        char.series.items.forEach {
            series = series + it.name + "\n"
        }
        intent.putExtra("series", series)

        startActivity(intent)
    }
}
