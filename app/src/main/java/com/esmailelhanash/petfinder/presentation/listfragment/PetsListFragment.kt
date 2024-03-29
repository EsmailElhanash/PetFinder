package com.esmailelhanash.petfinder.presentation.listfragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.esmailelhanash.petfinder.R
import com.esmailelhanash.petfinder.models.Animal
import com.esmailelhanash.petfinder.presentation.FragmentChangeListener
import com.esmailelhanash.petfinder.presentation.PetsViewModel
import com.esmailelhanash.petfinder.presentation.detailsfragment.PetDetailsFragment

class PetsListFragment : Fragment(), PetsTypesRecyclerViewAdapter.ItemClickListener, PetsListRecyclerView.ItemClickListener {


    // fragment tag
    companion object {
        val TAG: String = PetsListFragment::class.java.simpleName
    }

    private var fragmentChangeListener: FragmentChangeListener? = null
    // viewmodel
    private val petsViewModel: PetsViewModel by lazy {
        ViewModelProvider(requireActivity())[PetsViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        petsViewModel.getAllAnimals()
        petsViewModel.getTypes()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is FragmentChangeListener) {
            fragmentChangeListener = context
        } else {
            throw RuntimeException("$context must implement FragmentChangeListener")
        }
    }

    override fun onStart() {
        super.onStart()
        fragmentChangeListener?.onFragmentChange(TAG)
    }


    override fun onCreateView(
        inflater: android.view.LayoutInflater, container: android.view.ViewGroup?,
        savedInstanceState: Bundle?
    ): android.view.View? {

        val view = inflater.inflate(R.layout.fragment_pets_list, container, false)

        val petTypesRV = view.findViewById<RecyclerView>(R.id.pets_types_list)
        val petsListRV = view.findViewById<RecyclerView>(R.id.pets_list)

        listenMaxScrollEvent(petsListRV)

        observeAnimalTypes(petTypesRV)

        observeAnimalsList(petsListRV)

        petsViewModel.currentlyDisplayedType.observe(requireActivity()) {
            if ((petTypesRV.adapter as? PetsTypesRecyclerViewAdapter)
                    ?.selectType(it) == true
            ) {
                // successfully changed the selected type
                // destroy adapter
                petsListRV.adapter = null
            }
        }



        return view
    }

    // recycler view scroll listener
    private fun listenMaxScrollEvent(recyclerView: RecyclerView) {
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                // Check if scrolling downward (dy > 0)
                if (dy > 0) {
                    val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                    val visibleItemCount = layoutManager.childCount
                    val totalItemCount = layoutManager.itemCount
                    val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

                    if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                        && firstVisibleItemPosition >= 0 && petsViewModel.isLoading.value == false) {
                        // check the currently displayed type, even all, and load more animals from this type
                        petsViewModel.getMoreAnimals(petsViewModel.currentlyDisplayedType.value)
                    }
                }
            }
        })
    }

    private fun observeAnimalsList(petsListRV: RecyclerView) {
        petsViewModel.animals.observe(requireActivity()) {
            if (petsListRV.adapter == null) {
                petsListRV.adapter = PetsListRecyclerView(it, this)
                petsListRV.layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            }else {
                (petsListRV.adapter as? PetsListRecyclerView)?.updateList(it)
            }
        }
    }

    private fun observeAnimalTypes(petTypesRV: RecyclerView) {
        petsViewModel.types.observe(viewLifecycleOwner) {
            val currentSelectedType = petsViewModel.currentlyDisplayedType.value
            petTypesRV.adapter = PetsTypesRecyclerViewAdapter(it, this, currentSelectedType ?: "All")
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