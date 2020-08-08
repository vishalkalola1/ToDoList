package com.ezshare.todolist

import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.target.ViewTarget
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.ArrayList
import kotlin.math.log

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ViewTarget.setTagId(R.id.glide_tag);
        var data : List<GameList> = ArrayList<GameList>();
        val imageView = findViewById<ImageView>(R.id.imageView)
        val imageView1 = findViewById<ImageView>(R.id.imageView2)
        val imageView2 = findViewById<ImageView>(R.id.imageView3)
        val imageView3 = findViewById<ImageView>(R.id.imageView4)

        val callback  : Callback<List<GameList>> = object : Callback<List<GameList>>{
            override fun onFailure(call: Call<List<GameList>>, t: Throwable) {
                Log.d("xxx","error API Calling")
            }

            override fun onResponse(call: Call<List<GameList>>, response: Response<List<GameList>>) {
                if (response.code() == 200) {
                    val res = response.body()
                    if (res != null) {
                        data = res

                        var rnds = (0..(res.size-1)).random()
                        imageView.tag = rnds
                        Glide.with(this@MainActivity).load(res[rnds].picture).into(imageView)
                        rnds = (0..(res.size-1)).random()
                        imageView1.tag = rnds
                        Glide.with(this@MainActivity).load(res[rnds].picture).into(imageView1)
                        rnds = (0..(res.size-1)).random()
                        imageView2.tag = rnds
                        Glide.with(this@MainActivity).load(res[rnds].picture).into(imageView2)
                        rnds = (0..(res.size-1)).random()
                        imageView3.tag = rnds
                        Glide.with(this@MainActivity).load(res[rnds].picture).into(imageView3)
                    }
                }
            }
        }

        val baseURl = "https://androidlessonsapi.herokuapp.com/api/"
        val jsonConverter = GsonConverterFactory.create(GsonBuilder().create())
        val retrofit = Retrofit.Builder().baseUrl(baseURl).addConverterFactory(jsonConverter).build()
        val service = retrofit.create(WsInterface::class.java)
        service.getGameList().enqueue(callback)

        val clickListener = View.OnClickListener { view ->

            val intent = Intent(this, DetailsActivity::class.java)
            val index = view.tag.toString()
            val id = data[index.toInt()].id
            intent.putExtra("id", id)
            this.startActivity(intent);
        }

        imageView.setOnClickListener(clickListener)
        imageView1.setOnClickListener(clickListener)
        imageView2.setOnClickListener(clickListener)
        imageView3.setOnClickListener(clickListener)
    }
}