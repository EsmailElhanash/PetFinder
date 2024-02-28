package com.esmailelhanash.petfinder

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.test.core.app.ActivityScenario.launch
import com.esmailelhanash.petfinder.network.Network
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        // get animals coroutine scope
        CoroutineScope(Dispatchers.IO).launch {
            val animals = getAnimals()
            println(animals)
        }

    }


    private suspend fun getAnimals(): List<Animal> {

        return listOf()
    }

}