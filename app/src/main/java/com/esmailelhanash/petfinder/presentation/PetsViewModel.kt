package com.esmailelhanash.petfinder.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.esmailelhanash.petfinder.models.Animal
import com.esmailelhanash.petfinder.network.Network
import kotlinx.coroutines.launch


class PetsViewModel : ViewModel() {

    private val _animals = MutableLiveData<List<Animal>>()
    val animals: LiveData<List<Animal>> = _animals

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<String?>()
    val error: MutableLiveData<String?> = _error


    // the currently displayed animal type
    private val _currentlyDisplayedType = MutableLiveData<String>()
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

}