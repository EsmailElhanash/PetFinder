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
    ): android.view.View? {
        _binding = FragmentPetDetailsBinding.inflate(inflater, container, false)
        setImage()
        binding.nameTextView.text = binding.nameTextView.text.replace(Regex("xxx"), pet.name)
        binding.genderTextView.text = binding.genderTextView.text.replace(Regex("xxx"), pet.gender)
        binding.typeTextView.text = binding.typeTextView.text.replace(Regex("xxx"), pet.type)
        binding.sizeTextView.text = binding.sizeTextView.text.replace(Regex("xxx"), pet.size)
        binding.colorTextView.text = binding.colorTextView.text.replace(Regex("xxx"), pet.colors.primary ?: "N/A")
        binding.ageTextView.text = binding.ageTextView.text.replace(Regex("xxx"), pet.age)
//        binding.descriptionTextView.text = binding.descriptionTextView.text.replace(Regex("xxx"), pet.description?: "N/A")
        binding.statusTextView.text = binding.statusTextView.text.replace(Regex("xxx"), pet.status)




        return binding.root
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