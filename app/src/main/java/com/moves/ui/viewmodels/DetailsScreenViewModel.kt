package com.moves.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moves.domain.model.Films
import com.moves.domain.model.FilmsRepository
import com.moves.utils.ResultData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class DetailsScreenViewModel(private val repository: FilmsRepository) : ViewModel() {

    private val _details = MutableStateFlow<Films?>(null)
    val details = _details.asStateFlow()


    fun getFilmDetails(id: Int) {
        viewModelScope.launch {
            repository.getFilmById(id).collectLatest { result ->
                when (result) {
                    is ResultData.Success -> {
                        result.data?.let { films ->
                            _details.value = films
                        }
                    }
                    is ResultData.Error -> {

                    }
                }

            }
        }
    }
}