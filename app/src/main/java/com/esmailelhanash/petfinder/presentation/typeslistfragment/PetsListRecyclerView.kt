package com.esmailelhanash.petfinder.presentation.typeslistfragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.esmailelhanash.petfinder.R
import com.esmailelhanash.petfinder.models.Animal

class PetsListRecyclerView (private  val pets: List<Animal>): RecyclerView.Adapter<PetsListRecyclerView.PetsListViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PetsListRecyclerView.PetsListViewHolder {

        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.pet_info, parent, false)

        return PetsListViewHolder(view)
    }

    // extract all possible types from the list

    override fun onBindViewHolder(
        holder: PetsListViewHolder,
        position: Int
    ) {
        holder.bind()
    }




    override fun getItemCount(): Int = pets.size


    // view holder class for the pets types list
    inner class PetsListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind() {
            // name, gender, and type Text views
            val name = itemView.findViewById<TextView>(R.id.nameTextView)
            val gender = itemView.findViewById<TextView>(R.id.genderTextView)
            val type = itemView.findViewById<TextView>(R.id.typeTextView)

            // get the animal at the current position
            val pet = pets[adapterPosition]

            // set the name, gender, and type of the animal
            name.text = pet.name
            gender.text = pet.gender
            type.text = pet.type
        }





    }


}