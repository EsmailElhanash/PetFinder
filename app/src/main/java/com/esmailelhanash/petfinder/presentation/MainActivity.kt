package com.esmailelhanash.petfinder.presentation


import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import com.esmailelhanash.petfinder.R
import com.esmailelhanash.petfinder.databinding.ActivityMainBinding
import com.esmailelhanash.petfinder.presentation.detailsfragment.PetDetailsFragment
import com.esmailelhanash.petfinder.presentation.listfragment.PetsListFragment


class MainActivity : AppCompatActivity() , FragmentChangeListener{

    // main activity binding variable
    private lateinit var binding: ActivityMainBinding
    // viewmodel
    private val petsViewModel: PetsViewModel by lazy {
        ViewModelProvider(this)[PetsViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // init binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // init fragments
        inflateFragment()
        observeLoadingState()

    }

    private fun observeLoadingState() {
        // Assuming you have a ViewModel instance and the loading variable is LiveData<Boolean>
        petsViewModel.isLoading.observe(this) { isLoading ->
            if (isLoading) {
                // Show loading overlay
                binding. loadingOverlay.visibility = View.VISIBLE
            } else {
                // Hide loading overlay
                binding.loadingOverlay.visibility = View.GONE
            }
        }

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

    override fun onFragmentChange(fragmentTag: String) {
        when (fragmentTag) {
            PetsListFragment.TAG -> {
                binding.pageTitle.text = getString(R.string.pets)
                binding.backButton.visibility = View.GONE

            }
            PetDetailsFragment.TAG -> {
                binding.pageTitle.text = getString(R.string.pet_details)
                binding.backButton.apply {
                    visibility = View.VISIBLE
                    setOnClickListener {
                        supportFragmentManager.popBackStack()

                    }
                }
            }
        }
    }

}