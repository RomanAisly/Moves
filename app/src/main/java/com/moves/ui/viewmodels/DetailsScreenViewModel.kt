package com.moves.ui.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moves.domain.model.Films
import com.moves.domain.model.FilmsRepository
import com.moves.utils.ResultData
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class DetailsScreenViewModel(private val repository: FilmsRepository, savedStateHandle: SavedStateHandle) : ViewModel() {

    private val _details = MutableStateFlow<Films?>(null)
    val details = _details.asStateFlow()

    private val _toast = Channel<Boolean>()
    val toast = _toast.receiveAsFlow()

    private val _isFavorite = MutableStateFlow(false)
    val isFavorite = _isFavorite.asStateFlow()

    private val _isWatchLater = MutableStateFlow(false)
    val isWatchLater = _isWatchLater.asStateFlow()


    private val filmId = savedStateHandle.get<Int>("id")

    init {
        filmId?.let {
            getFilmDetails(it)
        }
    }

    fun changeFavorite() {
        _isFavorite.value = !_isFavorite.value
    }

    fun changeWatchLater() {
        _isWatchLater.value = !_isWatchLater.value
    }

    private fun getFilmDetails(id: Int) {
        viewModelScope.launch {
            repository.getFilmById(id).collectLatest { result ->
                when (result) {
                    is ResultData.Success -> {
                        result.data?.let { films ->
                            _details.value = films
                        }
                    }
                    is ResultData.Error -> {
                        _toast.send(true)
                    }
                }

            }
        }
    }
}