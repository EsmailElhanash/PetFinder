package com.esmailelhanash.petfinder.presentation


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import com.esmailelhanash.petfinder.R
import com.esmailelhanash.petfinder.presentation.listfragment.PetsListFragment


class MainActivity : AppCompatActivity() {


    // viewmodel
    private val petsViewModel: PetsViewModel by lazy {
        ViewModelProvider(this)[PetsViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        inflateFragment()


    }


    // inflate fragment function:
    private fun inflateFragment() {

        // Inside your activity
        val fragmentManager: FragmentManager = supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()

        val myFragment = PetsListFragment()


        // Replace R.id.fragmentContainer with the ID of your container in the XML
        fragmentTransaction.replace(R.id.fragmentContainer, myFragment)
        fragmentTransaction.commit()

    }

}