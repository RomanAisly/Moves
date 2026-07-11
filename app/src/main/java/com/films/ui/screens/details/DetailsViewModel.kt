package com.films.ui.screens.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.films.core.utils.AppError
import com.films.core.utils.AppSuccess
import com.films.core.utils.CheckDataResult
import com.films.data.local.SettingsManager
import com.films.domain.model.FilmsRepository
import com.films.ui.navigation.Routes
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DetailsViewModel(
    private val repository: FilmsRepository,
    private val settingsManager: SettingsManager,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = MutableStateFlow(DetailsState())
    val state = _state.asStateFlow()

    private val filmId = savedStateHandle.toRoute<Routes.Details>().id

    private val _snack = Channel<AppError>(Channel.BUFFERED)
    val snack = _snack.receiveAsFlow()

    private val _successSnack = Channel<AppSuccess>(Channel.BUFFERED)
    val successSnack = _successSnack.receiveAsFlow()

    private var favoriteJob: Job? = null
    private var watchLaterJob: Job? = null

    init {
        getFilmDetails(filmId)
        getTrailer(filmId)
    }

    private fun getFilmDetails(id: Int) {
        viewModelScope.launch {
            repository.getFilmById(id).collectLatest { result ->
                when (result) {
                    is CheckDataResult.Success -> {
                        result.data.let { film ->
                            _state.update {
                                it.copy(
                                    filmDetails = film,
                                    isFavorite = film.isFavorite,
                                    isWatchLater = film.isWatchLater
                                )
                            }
                        }
                    }

                    is CheckDataResult.Error -> {
                        _snack.send(result.error)
                    }
                }
            }
        }
    }

    private fun getTrailer(id: Int) {
        viewModelScope.launch {
            val currentLang = settingsManager.languageFlow.first().localeCode

            repository.getMovieTrailer(id, currentLang).collectLatest { result ->
                if (result is CheckDataResult.Success) {
                    _state.update { it.copy(trailerKey = result.data) }
                }
                // Ошибки трейлера можно игнорировать (не показывать юзеру),
                // просто трейлера не будет на экране
            }
        }
    }

    fun addToFavorite(isFavorite: Boolean) {
        _state.update { it.copy(isFavorite = isFavorite) }
        favoriteJob?.cancel()
        favoriteJob = viewModelScope.launch {
            delay(500)
            repository.updateFavoriteStatus(filmId, isFavorite)
            val message =
                if (isFavorite) AppSuccess.ADDED_TO_FAVORITES else AppSuccess.REMOVED_FROM_FAVORITES
            _successSnack.send(message)
        }
    }

    fun addToWatchLater(isWatchLater: Boolean) {
        _state.update { it.copy(isWatchLater = isWatchLater) }
        watchLaterJob?.cancel()
        watchLaterJob = viewModelScope.launch {
            delay(500)
            repository.updateWatchLaterStatus(filmId, isWatchLater)
            val message =
                if (isWatchLater) AppSuccess.ADDED_TO_WATCH_LATER else AppSuccess.REMOVED_FROM_WATCH_LATER
            _successSnack.send(message)
        }
    }
}