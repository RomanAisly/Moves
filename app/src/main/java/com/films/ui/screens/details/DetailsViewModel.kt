package com.films.ui.screens.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class DetailsViewModel(
//    private val repository: FilmsRepository,
//    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = MutableStateFlow(DetailsState())
    val state = _state.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = DetailsState()
    )

    //    private val filmId = savedStateHandle.toRoute<Routes.Details>().id
//
//    private val _snack = Channel<AppError>(Channel.BUFFERED)
//    val snack = _snack.receiveAsFlow()
//
//    init {
//        getFilmDetails(filmId)
//    }
//
//    private fun getFilmDetails(id: Int) {
//        viewModelScope.launch {
//            repository.getFilmById(id).collectLatest { result ->
//                when (result) {
//                    is CheckDataResult.Success -> {
//                        result.data.let { film ->
//                            _state.update { it.copy(filmDetails = film) }
//                        }
//                    }
//
//                    is CheckDataResult.Error -> {
//                        _snack.send(result.error)
//                    }
//                }
//            }
//        }
//    }
    fun addToFavorite(isFavorite: Boolean) {
        _state.update { it.copy(isFavorite = isFavorite) }
    }

    fun addToWatchLater(isWatchLater: Boolean) {
        _state.update { it.copy(isWatchLater = isWatchLater) }
    }
}