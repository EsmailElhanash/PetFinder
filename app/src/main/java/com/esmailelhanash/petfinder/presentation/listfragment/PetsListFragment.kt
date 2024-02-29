package com.esmailelhanash.petfinder.presentation.listfragment

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
                val recyclerView = view.findViewById<RecyclerView>(R.id.pets_types_list)
                recyclerView.adapter = PetsTypesRecyclerViewAdapter(it)

                // layout manager, with horizontal orientation

                recyclerView.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
            }
        }



        return view
    }


}