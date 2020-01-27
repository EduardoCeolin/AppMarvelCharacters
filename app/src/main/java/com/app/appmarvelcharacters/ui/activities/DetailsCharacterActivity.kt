package com.app.appmarvelcharacters.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.app.appmarvelcharacters.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_details_character.*

class DetailsCharacterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details_character)

        Picasso.get()
            .load(intent.getStringExtra("image"))
            .into(img_char)

        name_char.text = intent.getStringExtra("name")

        if(intent.getStringExtra("description") == ""){
            description.text = "No description available."
        }else {
            description.text = intent.getStringExtra("description")
        }

        comics.text = name_char.text as String? + " appeared " + intent.getStringExtra("appearances") +" times in the comics."

        appearances.text = "Appearances: "+"\n" +intent.getStringExtra("appearancesList")

        series.text = "Series: "+"\n"+ intent.getStringExtra("series")
    }
}
