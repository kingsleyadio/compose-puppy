package com.example.androiddevchallenge.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androiddevchallenge.Injection
import com.example.androiddevchallenge.data.Puppy
import com.example.androiddevchallenge.data.PuppyRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.stateIn

/**
 * @author Kingsley Adio
 * @since 02 Mar, 2021
 */
class PuppyListViewModel(
    private val puppyRepository: PuppyRepository = Injection.puppyRepository
) : ViewModel() {

    val puppies = flow { emit(puppyRepository.getPuppies()) }
        .flowOn(Dispatchers.IO)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    fun findPuppyById(pupId: Int): Puppy? {
        return puppies.value.find { it.id == pupId }
    }
}
