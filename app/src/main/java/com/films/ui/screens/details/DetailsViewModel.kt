package com.films.ui.screens.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.films.core.utils.AppError
import com.films.core.utils.AppSuccess
import com.films.core.utils.CheckDataResult
import com.films.domain.model.FilmsRepository
import com.films.ui.navigation.Routes
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DetailsViewModel(
    private val repository: FilmsRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = MutableStateFlow(DetailsState())
    val state = _state.asStateFlow()

    private val filmId = savedStateHandle.toRoute<Routes.Details>().id

    private val _snack = Channel<AppError>(Channel.BUFFERED)
    val snack = _snack.receiveAsFlow()

    private val _successSnack = Channel<AppSuccess>(Channel.BUFFERED)
    val successSnack = _successSnack.receiveAsFlow()

    init {
        getFilmDetails(filmId)
    }

    private fun getFilmDetails(id: Int) {
        viewModelScope.launch {
            repository.getFilmById(id).collectLatest { result ->
                when (result) {
                    is CheckDataResult.Success -> {
                        result.data.let { film ->
                            _state.update { it.copy(filmDetails = film) }
                        }
                    }

                    is CheckDataResult.Error -> {
                        _snack.send(result.error)
                    }
                }
            }
        }
    }

    fun addToFavorite(isFavorite: Boolean) {
        _state.update { it.copy(isFavorite = isFavorite) }

        viewModelScope.launch {
            val message =
                if (isFavorite) AppSuccess.ADDED_TO_FAVORITES else AppSuccess.REMOVED_FROM_FAVORITES
            _successSnack.send(message)
        }
    }

    fun addToWatchLater(isWatchLater: Boolean) {
        _state.update { it.copy(isWatchLater = isWatchLater) }

        viewModelScope.launch {
            val message =
                if (isWatchLater) AppSuccess.ADDED_TO_WATCH_LATER else AppSuccess.REMOVED_FROM_WATCH_LATER
            _successSnack.send(message)
        }
    }
}