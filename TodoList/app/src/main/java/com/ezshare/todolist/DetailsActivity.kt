package com.ezshare.todolist

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        var url = ""
        val img = findViewById<ImageView>(R.id.imageView6)
        val namefield = findViewById<TextView>(R.id.nametxt)
        val typefield = findViewById<TextView>(R.id.typetxt)
        val MbPlayertxt = findViewById<TextView>(R.id.MbPlayertxt)
        val Yeartxt = findViewById<TextView>(R.id.Yeartxt)
        val txtDescription = findViewById<TextView>(R.id.txtDescription)
        val knowMoreButton = findViewById<Button>(R.id.knowMoreButton)

        val id = intent.extras?.get("id") as Int

        val callbacktemp  : Callback<GameDetails> = object : Callback<GameDetails> {
            override fun onFailure(call: Call<GameDetails>, t: Throwable) {
                Log.d("xxx","error API Calling")
            }

            override fun onResponse(call: Call<GameDetails>, response: Response<GameDetails>) {
                if (response.code() == 200) {
                    val res = response.body()
                    Log.d("Success","Test")
                    if (res != null) {
                        Glide.with(this@DetailsActivity).load(res.picture).into(img)
                        namefield.text = res.name
                        typefield.text = res.type
                        MbPlayertxt.text = res.players.toString()
                        Yeartxt.text = res.year.toString()
                        txtDescription.text = res.description_en
                        url = res.url
                    }
                }
            }
        }

        val baseURl = "https://androidlessonsapi.herokuapp.com/api/"
        val jsonConverter = GsonConverterFactory.create(GsonBuilder().create())
        val retrofit = Retrofit.Builder().baseUrl(baseURl).addConverterFactory(jsonConverter).build()
        val service = retrofit.create(WsInterface::class.java)
        service.getGameDetails(id).enqueue(callbacktemp)

        knowMoreButton.setOnClickListener {
            val uri = Uri.parse(url)
            startActivity(Intent(Intent.ACTION_VIEW, uri))
        }
    }
}