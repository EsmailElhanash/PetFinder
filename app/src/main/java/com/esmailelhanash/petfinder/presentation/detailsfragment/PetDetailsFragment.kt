package com.esmailelhanash.petfinder.presentation.detailsfragment

import android.content.res.Resources
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.esmailelhanash.petfinder.R
import com.esmailelhanash.petfinder.databinding.FragmentPetDetailsBinding
import com.esmailelhanash.petfinder.models.Animal

class PetDetailsFragment(private val pet: Animal) :  Fragment() {

    private var _binding: FragmentPetDetailsBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: android.view.LayoutInflater, container: android.view.ViewGroup?,
        savedInstanceState: Bundle?
    ): android.view.View {
        _binding = FragmentPetDetailsBinding.inflate(inflater, container, false)
        setImage()
        binding.nameTextView.text = binding.nameTextView.text.replace(Regex("xxx"), getPetNameString())
        binding.sizeTextView.text = binding.sizeTextView.text.replace(Regex("xxx"), getPetSizeString())
        binding.colorTextView.text = binding.colorTextView.text.replace(Regex("xxx"), getPetColorString())
        binding.addressTextView.text = binding.addressTextView.text.replace(Regex("xxx"),getAddressString())




        return binding.root
    }

    private fun getAddressString(): String {
        //Address in the following format: city, state, country, or "N/A"
        return try {
            pet.contact.address.city + ", " + pet.contact.address.state + ", " + pet.contact.address.country
        }
        catch (e: Exception) {
            "N/A"
        }
    }

    private fun getPetSizeString() : String {
        // if size is null or empty return "N/A"
        return pet.size.ifEmpty { "N/A" }
    }

    // if pet name is null or empty return "N/A"
    private fun getPetNameString() : String {
        // if name is null or empty return "N/A"
        return pet.name.ifEmpty { "N/A" }
    }

    // if color is null or empty return "N/A"
    private fun getPetColorString() : String {
        // if color is null or empty return "N/A"
        return pet.colors.primary?.ifEmpty { "N/A" } ?: "N/A"
    }



    // set image of the pet:
    private fun setImage() {
        val screenWidth = Resources.getSystem().displayMetrics.widthPixels
        val targetSize = (0.8 * screenWidth).toInt()
//        binding.petImageView.layoutParams.width = targetSize
        binding.petImageView.layoutParams.height = targetSize

        if (pet.photos.isNotEmpty()) {
            Glide.with(requireContext())
                .load(pet.photos[0].medium) // Set both width and height to create a square image
                .placeholder(R.drawable.ph)
                .into(binding.petImageView)
        } else {
            // Handle the case when the photos list is empty, maybe set a default image or take another action.
        }
    }

}