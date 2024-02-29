package com.esmailelhanash.petfinder.presentation.typeslistfragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.esmailelhanash.petfinder.R
import com.esmailelhanash.petfinder.presentation.PetsViewModel

class PetsListFragment : Fragment() {

    // viewmodel
    private val petsViewModel: PetsViewModel by lazy {
        ViewModelProvider(this)[PetsViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        petsViewModel.getAllAnimals()
    }

    override fun onCreateView(
        inflater: android.view.LayoutInflater, container: android.view.ViewGroup?,
        savedInstanceState: Bundle?
    ): android.view.View? {

        val view = inflater.inflate(R.layout.fragment_pets_list, container, false)

        petsViewModel.animals.observe(requireActivity()){

            if (it != null) {
                val petTypesRV = view.findViewById<RecyclerView>(R.id.pets_types_list)
                petTypesRV.adapter = PetsTypesRecyclerViewAdapter(it)
                petTypesRV.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)

                val petsRV = view.findViewById<RecyclerView>(R.id.pets_list)
                petsRV.adapter = PetsListRecyclerView(it)
                petsRV.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)

            }
        }



        return view
    }


}