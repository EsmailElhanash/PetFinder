package com.esmailelhanash.petfinder.presentation.fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.esmailelhanash.petfinder.R
import com.esmailelhanash.petfinder.models.Animal


// implement a recyclerview adapter for the pets types list with custom view holder and item click listener
class PetsTypesRecyclerViewAdapter(items: List<Animal>, private val itemClickListener: ItemClickListener) : RecyclerView.Adapter<PetsTypesRecyclerViewAdapter.PetsTypesViewHolder>() {

    // get all distinct types from animals items list and add a new item "all" to the beginning of all the types,
    // then replicate 4 times
    private val types = items.map { it.type }.distinct().toMutableList().apply { add(0,"All") }

    private var selectedType: String = "All"


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
        holder.itemView.setOnClickListener {
            itemClickListener.onItemClicked(types[position])
        }
        holder.bind()
    }

    // set the selected item in the list, and change its background color to darker
    fun selectType(type: String) {
        selectedType = type
        notifyDataSetChanged()

    }




    override fun getItemCount(): Int = types.size


    interface ItemClickListener {
        fun onItemClicked(type:String)
        // Add more methods for other events if needed

    }

    // view holder class for the pets types list
    inner class PetsTypesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind() {
            val textView = itemView.findViewById<TextView>(R.id.pets_type_name)
            textView.text = types[adapterPosition]
            if (selectedType == types[adapterPosition]) {
                itemView.setBackgroundResource(R.drawable.rounded_darkgreen_bg)
            }else{
                itemView.setBackgroundResource(R.drawable.rounded_lightgreen_bg)
            }


        }





    }


}