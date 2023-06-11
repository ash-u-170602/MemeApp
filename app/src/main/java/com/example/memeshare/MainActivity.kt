package com.example.memeshare

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.memeshare.databinding.ActivityMainBinding
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    var url: String?=null
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    @OptIn(DelicateCoroutinesApi::class)
    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        loadMeme()
    }


    // Load meme----------------------------------------------------------------------------------
    @OptIn(DelicateCoroutinesApi::class)
    fun loadMeme(){
        binding.loading.visibility = View.VISIBLE
        val quotesApi = retrofitInstance.getInstance().create(memeApi::class.java)

        GlobalScope.launch(Dispatchers.Main) {
            val result = quotesApi.getMeme()
            val data = result.body() as Meme
            url = data.url
        }
        loadUrlImage()
    }

    // Load url image -------------------------------------------------------------------------
    fun loadUrlImage() {
        Glide.with(this)
            .load(url)
            .placeholder(R.drawable.loading)
            .into(binding.meme)
        binding.loading.visibility = View.GONE
    }

    // Load next meme url----------------------------------------------------------------------
    @OptIn(DelicateCoroutinesApi::class)
    fun loadNextMeme() {
        val quotesApi = retrofitInstance.getInstance().create(memeApi::class.java)

        GlobalScope.launch(Dispatchers.Main) {
            val result = quotesApi.getMeme()
            val data = result.body() as Meme
            url = data.url
        }
    }

    // Share button-----------------------------------------------------------------------------
    fun Share(view: View) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_TEXT, "Checkout this meme $url")
        val chooser = Intent.createChooser(intent, "Share meme using...")
        startActivity(chooser)
    }

    // Next meme---------------------------------------------------------------------------------
    @OptIn(DelicateCoroutinesApi::class)
    fun Next(view: View) {
       loadMeme()
        loadNextMeme()
    }

}