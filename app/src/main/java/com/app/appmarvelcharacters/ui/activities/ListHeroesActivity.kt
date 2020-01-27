package com.app.appmarvelcharacters.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.app.appmarvelcharacters.R
import com.app.appmarvelcharacters.ui.fragments.ListHeroesFragment

class ListHeroesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.list_heroes_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container,
                    ListHeroesFragment.newInstance()
                )
                .commitNow()
        }
    }

}
