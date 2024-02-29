package com.esmailelhanash.petfinder.presentation.listfragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.esmailelhanash.petfinder.R
import com.esmailelhanash.petfinder.models.Animal
import com.esmailelhanash.petfinder.presentation.PetsViewModel
import com.esmailelhanash.petfinder.presentation.detailsfragment.PetDetailsFragment

class PetsListFragment : Fragment(), PetsTypesRecyclerViewAdapter.ItemClickListener, PetsListRecyclerView.ItemClickListener {

    // viewmodel
    private val petsViewModel: PetsViewModel by lazy {
        ViewModelProvider(this)[PetsViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        petsViewModel.getAllAnimals()
        petsViewModel.getTypes()
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
                petsListRV.adapter = PetsListRecyclerView(it,this)
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
                        PetsListRecyclerView(allPets,this)
                        else PetsListRecyclerView(allPets.filter { pet ->
                        pet.type == it

                    },this)
                }

            }
        }



        return view
    }

    override fun onItemClicked(type: String) {
        petsViewModel.setCurrentlyDisplayedType(type)
    }

    override fun onItemClicked(animal: Animal) {
        // In your FirstFragment where you want to navigate to the SecondFragment
        val petDetailsFragment = PetDetailsFragment(animal)
        val transaction = requireActivity().supportFragmentManager.beginTransaction()

        // Replace the current fragment with the new one
        transaction.replace(R.id.fragmentContainer, petDetailsFragment)

        // Optionally, add the transaction to the back stack
        transaction.addToBackStack(null)

        // Commit the transaction
        transaction.commit()

    }


}