package com.esmailelhanash.petfinder

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.esmailelhanash.petfinder.network.Network
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        CoroutineScope(Dispatchers.IO).launch {
            Network.getAllAnimals()
        }
    }

}