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



    // all types livedata
    private val _types = MutableLiveData<List<AnimalType>>()

    val types: LiveData<List<AnimalType>> = _types

    // the currently displayed animal type
    // set default selected type to "all"
    private val _currentlyDisplayedType = MutableLiveData<String>().apply {
        value = "All"
    }


    val currentlyDisplayedType: LiveData<String> = _currentlyDisplayedType

    fun getAllAnimals(type: String? = null) {
        _isLoading.value = true
        _error.value = null

        viewModelScope.launch {
            try {
                if (type != null && type != "All") {
                    Network.getAnimalsOfType(type)?.let {
                        updateList(it.toMutableList())
                    }
                } else {
                    Network.getAllAnimals()?.let {
                        updateList(it.toMutableList())
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

    fun getMoreAnimals(animalType: String? = null) {
        _isLoading.value = true
        _error.value = null

        viewModelScope.launch {
            try {
                if (animalType != null && animalType != "All") {
                    Network.getMoreAnimalsOfType(nextPageToBeLoaded, animalType)?.let { newAnimals ->
                        _animals.value.apply {
                            updateList(newAnimals.toMutableList())
                        }
                        nextPageToBeLoaded++
                    }
                } else {
                    Network.getMoreAnimals(nextPageToBeLoaded)?.let { newAnimals ->
                        _animals.value.apply {
                            updateList(newAnimals.toMutableList())
                        }
                        nextPageToBeLoaded++
                    }
                }
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }


    // function to set the currently displayed animal type
    fun setCurrentlyDisplayedType(type: String) {
        // if same currently displayed type, do nothing, return, else update the currently displayed type, reset the page number, and reset the animals list
        if (type == _currentlyDisplayedType.value) {
            return
        }
        _currentlyDisplayedType.value = type
        nextPageToBeLoaded = 2

        _animals.value = mutableListOf()
        if (type == "All")
            getAllAnimals()
        else
            getAllAnimals(type)
    }

    // load animals of a specific type


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