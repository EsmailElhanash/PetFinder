package com.esmailelhanash.petfinder.presentation.fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.esmailelhanash.petfinder.R
import com.esmailelhanash.petfinder.models.Animal


// implement a recyclerview adapter for the pets types list with custom view holder and item click listener
class PetsTypesRecyclerViewAdapter(items: List<Animal>) : RecyclerView.Adapter<PetsTypesRecyclerViewAdapter.PetsTypesViewHolder>() {

    // get all distinct types from animals items list and add a new item "all" to the beginning of all the types,
    // then replicate 4 times
    private val types = items.map { it.type }.distinct().toMutableList().apply { add(0,"All") }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PetsTypesViewHolder {

        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.pet_type, parent, false)

        return PetsTypesViewHolder(view)
    }

    // extract all possible types from the list

    override fun onBindViewHolder(
        holder: PetsTypesViewHolder,
        position: Int
    ) {
        holder.bind()
    }




    override fun getItemCount(): Int = types.size


    // view holder class for the pets types list
    inner class PetsTypesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind() {
            val textView = itemView.findViewById<TextView>(R.id.pets_type_name)
            textView.text = types[adapterPosition]
        }





    }


}