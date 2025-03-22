package com.moves.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moves.domain.model.Films
import com.moves.domain.model.FilmsRepository
import com.moves.utils.FilmsCategory
import com.moves.utils.ResultData
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class HomeScreenState(
    val isLoading: Boolean = false,
    val films: List<Films> = emptyList()
)

class HomeScreenViewModel(private val repository: FilmsRepository) :
    ViewModel() {

    private val _state = MutableStateFlow(HomeScreenState())
    val state: StateFlow<HomeScreenState> = _state.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = HomeScreenState()
    )


    private val _toast = Channel<Boolean>()
    val toast = _toast.receiveAsFlow()

    init {
        showAllFilms()
    }

    private fun showAllFilms() {
        viewModelScope.launch {
            repository.getFilms(
                page = 1,
                forceFetch = false,
                category = FilmsCategory.POPULAR
            ).onStart {
                _state.update { it.copy(isLoading = true) }
            }.collectLatest { result ->
                when (result) {
                    is ResultData.Success -> {
                        result.data?.let { films ->
                            _state.update { it.copy(isLoading = false, films = films) }
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