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

        observeAnimalTypes(petTypesRV)

        observeAnimalsList(petsListRV)

        petsViewModel.currentlyDisplayedType.observe(requireActivity()){
            if (it!= null) {
                (petTypesRV.adapter as? PetsTypesRecyclerViewAdapter)
                    ?.selectType(it)

                val allPets = petsViewModel.animals.value
                if (allPets!= null) {
                    if (it == "All") {
                        petsListRV.adapter = PetsListRecyclerView(allPets, this)
                    }
                }

            }
        }



        return view
    }

    // observe animals of a specific type
    private fun observeAnimalsListOfType( petsListRV: RecyclerView) {
        petsViewModel.animalsOfTypes.observe(requireActivity()) {
            petsViewModel.currentlyDisplayedType.value.apply {
                if (!isNullOrEmpty() ){
                    petsListRV.adapter = PetsListRecyclerView(it[this]!!, this@PetsListFragment)
                }
            }


        }
    }

    private fun observeAnimalsList(petsListRV: RecyclerView) {
        petsViewModel.animals.observe(requireActivity()) {
            if (it != null) {
                petsListRV.adapter = PetsListRecyclerView(it, this)
                petsListRV.layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

            }
        }
    }

    private fun observeAnimalTypes(petTypesRV: RecyclerView) {
        petsViewModel.types.observe(viewLifecycleOwner) {

            petTypesRV.adapter = PetsTypesRecyclerViewAdapter(it, this)
            petTypesRV.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }
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