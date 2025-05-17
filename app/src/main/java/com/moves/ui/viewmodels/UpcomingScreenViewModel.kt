package com.moves.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moves.domain.model.FilmsRepository
import com.moves.ui.events.HomeScreenEvents
import com.moves.ui.states.HomeScreenState
import com.moves.utils.FilmsCategory
import com.moves.utils.CheckDataResult
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class UpcomingScreenViewModel(private val repository: FilmsRepository) :
    ViewModel() {
    private val _state = MutableStateFlow(HomeScreenState())
    val state: StateFlow<HomeScreenState> = _state.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = HomeScreenState()
    )


    private val _toast = Channel<Boolean>(Channel.BUFFERED)
    val toast = _toast.receiveAsFlow()

    init {
        onEvent(HomeScreenEvents.ShowFilms)
    }

    fun onEvent(event: HomeScreenEvents) {
        when (event) {
            is HomeScreenEvents.ShowFilms -> {
                viewModelScope.launch {
                    repository.getFilms(
                        page = 1,
                        forceFetch = false,
                        category = FilmsCategory.UPCOMING
                    ).collectLatest { result ->
                        when (result) {
                            is CheckDataResult.Success -> {
                                result.data.let { films ->
                                    _state.update { it.copy(films = films) }
                                }
                            }

                            is CheckDataResult.Error -> {
                                _toast.send(true)
                            }
                        }
                    }
                }
            }

            is HomeScreenEvents.ChangeCategory -> {}
        }
    }
}