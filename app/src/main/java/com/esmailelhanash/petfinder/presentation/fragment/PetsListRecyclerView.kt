package com.esmailelhanash.petfinder.presentation.fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
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

            // image
            val image = itemView.findViewById<ImageView>(R.id.petPhoto)




            // get the animal at the current position
            val pet = pets[adapterPosition]

            // set the name, gender, and type of the animal
            name.text = name.text.replace(Regex("xxx"), pet.name)

            gender.text = gender.text.replace(Regex("xxx"), pet.gender)
            type.text = type.text.replace(Regex("xxx"), pet.type)

            if (pet.photos.isNotEmpty()) {
                Glide.with(image.context)
                    .load(pet.photos[0].small)
                    .placeholder(R.drawable.ph)
                    .into(image)
            } else {
                // Handle the case when the photos list is empty, maybe set a default image or take another action.
            }

        }





    }


}