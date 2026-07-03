package com.moves.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moves.core.utils.CheckDataResult
import com.moves.domain.model.FilmsRepository
import com.moves.ui.components.FilmCategory
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

class HomeScreenViewModel(
    private val repository: FilmsRepository
) : ViewModel() {

    private val _state = MutableStateFlow(HomeScreenState())
    val state: StateFlow<HomeScreenState> = _state.onStart {
        loadFilms()
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = HomeScreenState()
    )

    private val _toast = Channel<Boolean>(Channel.Factory.BUFFERED)
    val toast = _toast.receiveAsFlow()

    private fun loadFilms() {
        viewModelScope.launch {
            repository.getFilms(
                page = 1,
                language = "en-US",
                forceFetch = false,
                category = _state.value.selectedCategory
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


    fun changeCategory(category: FilmCategory) {
        _state.update { it.copy(selectedCategory = category.category) }
        loadFilms()
    }
}