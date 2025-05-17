package com.moves.ui.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.moves.domain.model.FilmsRepository
import com.moves.domain.navigation.Screens
import com.moves.ui.events.DetailScreenEvents
import com.moves.ui.states.DetailsScreenState
import com.moves.utils.CheckDataResult
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class DetailsScreenViewModel(
    private val repository: FilmsRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = MutableStateFlow(DetailsScreenState())
    val state = _state.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = DetailsScreenState()
    )

    private val _toast = Channel<Boolean>(Channel.BUFFERED)
    val toast = _toast.receiveAsFlow()

    private val filmId = savedStateHandle.toRoute<Screens.Details>().id

    fun onEvent(event: DetailScreenEvents) {
        when (event) {
            is DetailScreenEvents.UpdateFavorite -> {
                _state.value = _state.value.copy(isFavorite = !event.isFavorite)
            }

            is DetailScreenEvents.UpdateWatchLater -> {
                _state.value = _state.value.copy(isWatchLater = !event.isWatchLater)
            }

            is DetailScreenEvents.GetFilmDetails -> {
                getFilmDetails(id = filmId)
            }
        }
    }

    private fun getFilmDetails(id: Int) {
        viewModelScope.launch {
            repository.getFilmById(id).collectLatest { result ->
                when (result) {
                    is CheckDataResult.Success -> {
                        result.data.let { films ->
                            _state.value = _state.value.copy(filmDetails = films)
                        }
                    }

                    is CheckDataResult.Error -> {
                        _toast.send(true)
                    }
                }
            }
        }
    }
}