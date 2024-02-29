package com.esmailelhanash.petfinder.presentation.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.esmailelhanash.petfinder.R
import com.esmailelhanash.petfinder.presentation.PetsViewModel

class PetsListFragment : Fragment(), PetsTypesRecyclerViewAdapter.ItemClickListener {

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

        val petTypesRV = view.findViewById<RecyclerView>(R.id.pets_types_list)
        val petsListRV = view.findViewById<RecyclerView>(R.id.pets_list)
        petsViewModel.animals.observe(requireActivity()){
            if (it != null) {
                petTypesRV.adapter = PetsTypesRecyclerViewAdapter(it,this)
                petTypesRV.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
                petsListRV.adapter = PetsListRecyclerView(it)
                petsListRV.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)

            }
        }

        petsViewModel.currentlyDisplayedType.observe(requireActivity()){
            if (it!= null) {
                // change the bg of currently displayed type in the types adapter to dark green
                (petTypesRV.adapter as? PetsTypesRecyclerViewAdapter)
                    ?.selectType(it)

                val allPets = petsViewModel.animals.value
                if (allPets!= null) {
                    petsListRV.adapter = if (it == "All")
                        PetsListRecyclerView(allPets)
                        else PetsListRecyclerView(allPets.filter { pet ->
                        pet.type == it

                    })
                }

            }
        }



        return view
    }

    override fun onItemClicked(type: String) {
        petsViewModel.setCurrentlyDisplayedType(type)
    }


}