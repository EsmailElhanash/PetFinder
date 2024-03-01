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


    private val _animals = MutableLiveData<MutableList<Animal>>().apply {
        value = mutableListOf()
        // when reset of the list, we want to reset the page number to 2
        nextPageToBeLoaded = 2
    }
    val animals: LiveData<MutableList<Animal>> = _animals

    private var nextPageToBeLoaded = 2

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<String?>()
    val error: MutableLiveData<String?> = _error


    // a mutable live data to store a map between type names and their animals list
    private val _animalsOfTypes = MutableLiveData<MutableMap<String, MutableList<Animal>>>().apply {
        value = mutableMapOf()
    }


    private var lastLoadedPageForAnimalType = mutableMapOf<String,Int>()

    val animalsOfTypes: LiveData<MutableMap<String, MutableList<Animal>>> = _animalsOfTypes


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
                Network.getAllAnimals().apply {
                    if (this!= null) {
                        updateList(this.toMutableList())
                    }
                }

            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }

    private fun updateList(newAnimals: MutableList<Animal>) {
        // if the list is empty, post immediately, or if not empty, add the new animals to the end of the list
        if (_animals.value?.isEmpty() == true) {
            _animals.postValue(newAnimals)
        } else {
            _animals.postValue(_animals.value?.plus(newAnimals)?.toMutableList())
        }
    }

    fun getMoreAnimals() {
        _isLoading.value = true
        _error.value = null
        viewModelScope.launch {
            try {
                Network.getMoreAnimals(nextPageToBeLoaded)?.let { newAnimals ->
                    _animals.value.apply {
                        updateList(newAnimals.toMutableList())
                    }
                    nextPageToBeLoaded++
                }
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun getMoreAnimalsOfType(animalType: String) {

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
                            put(type, it.toMutableList())
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