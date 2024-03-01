package com.esmailelhanash.petfinder.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.esmailelhanash.petfinder.models.Animal
import com.esmailelhanash.petfinder.models.AnimalType
import com.esmailelhanash.petfinder.network.Network
import kotlinx.coroutines.launch


class PetsViewModel : ViewModel() {

    private val _animals = MutableLiveData<List<Animal>>()
    val animals: LiveData<List<Animal>> = _animals

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<String?>()
    val error: MutableLiveData<String?> = _error


    // a mutable live data to store a map between type names and their animals list
    private val _animalsOfTypes = MutableLiveData<MutableMap<String, List<Animal>>>().apply {
        value = mutableMapOf()
    }
    val animalsOfTypes: LiveData<MutableMap<String, List<Animal>>> = _animalsOfTypes


    // all types livedata
    private val _types = MutableLiveData<List<AnimalType>>()
    val types: LiveData<List<AnimalType>> = _types

    // the currently displayed animal type
    // set default selected type to "all"
    private val _currentlyDisplayedType = MutableLiveData<String>().apply {
        value = "All"
    }


    val currentlyDisplayedType: LiveData<String> = _currentlyDisplayedType

    fun getAllAnimals() {
        _isLoading.value = true
        _error.value = null
        viewModelScope.launch {
            try {
                _animals.value = Network.getAllAnimals()
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }

    // function to set the currently displayed animal type
    fun setCurrentlyDisplayedType(type: String) {
        _currentlyDisplayedType.value = type
        getAnimalsOfType(type)
    }

    // load animals of a specific type
    private fun getAnimalsOfType(type: String) {
        _isLoading.value = true
        _error.value = null
        viewModelScope.launch {
            try {
                // replace or update the current entry of the map with key = type
                Network.getAnimalsOfType(type)?.let {

                    _animalsOfTypes.postValue(
                        _animalsOfTypes.value?.apply {
                            put(type, it)
                        }
                    )
                }
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun getTypes() {
        _isLoading.value = true
        _error.value = null
        viewModelScope.launch {
            try {
                _types.value = Network.getAllTypes()
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }

}