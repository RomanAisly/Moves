package com.moves.ui.viewmodels

import androidx.compose.ui.text.intl.Locale
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moves.domain.model.FilmsRepository
import com.moves.ui.events.HomeScreenEvents
import com.moves.ui.states.HomeScreenState
import com.moves.utils.CheckDataResult
import com.moves.utils.FilmsLanguages
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeScreenViewModel(private val repository: FilmsRepository) :
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
        onEvent(HomeScreenEvents.ChangeLanguage)
    }

    fun onEvent(event: HomeScreenEvents) {
        when (event) {
            is HomeScreenEvents.ShowFilms -> {
                viewModelScope.launch {
                    repository.getFilms(
                        page = 1,
                        language = _state.value.language,
                        forceFetch = false,
                        category = _state.value.category
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

            is HomeScreenEvents.ChangeCategory -> {
                _state.update { it.copy(category = event.category) }
                onEvent(HomeScreenEvents.ShowFilms)
            }

            is HomeScreenEvents.ChangeLanguage -> {
                _state.update {
                    it.copy(
                        language = when (Locale.current.language) {
                            "ru" -> FilmsLanguages.RUSSIAN
                            "uk" -> FilmsLanguages.UKRAINIAN
                            "de" -> FilmsLanguages.GERMAN
                            "fr" -> FilmsLanguages.FRENCH
                            "it" -> FilmsLanguages.ITALIAN
                            "es" -> FilmsLanguages.SPANISH
                            "pt" -> FilmsLanguages.PORTUGUESE
                            "ko" -> FilmsLanguages.KOREAN
                            "ja" -> FilmsLanguages.JAPANESE
                            else -> FilmsLanguages.ENGLISH
                        }
                    )
                }
            }
        }
    }
}