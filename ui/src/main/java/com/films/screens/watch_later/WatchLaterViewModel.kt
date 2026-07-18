package com.films.screens.watch_later

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.films.Films
import com.films.FilmsRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class WatchLaterViewModel(repository: FilmsRepository) : ViewModel() {

    val watchLaterFilms: StateFlow<List<Films>> = repository.getWatchLaterFilms()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
}