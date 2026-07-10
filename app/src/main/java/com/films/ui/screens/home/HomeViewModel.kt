package com.films.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.films.core.utils.AppError
import com.films.core.utils.CheckDataResult
import com.films.data.local.SettingsManager
import com.films.domain.model.FilmsRepository
import com.films.ui.components.FilmCategory
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModel(
    private val repository: FilmsRepository,
    private val settingsManager: SettingsManager
) : ViewModel() {

    private val _state = MutableStateFlow(HomeState())
    val state = _state.asStateFlow()

    private val _snack = Channel<AppError>(Channel.BUFFERED)
    val snack = _snack.receiveAsFlow()

    private val loadAction = MutableSharedFlow<Boolean>(
        replay = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    init {
        loadAction.tryEmit(false)

        loadAction
            .flatMapLatest { forceFetch ->
                if (forceFetch) _state.update { it.copy(isRefreshing = true) }
                val currentLanguage = settingsManager.languageFlow.first()
                repository.getFilms(
                    page = 1,
                    language = currentLanguage.localeCode,
                    forceFetch = forceFetch,
                    category = _state.value.selectedCategory
                )
            }
            .onEach { result ->
                when (result) {
                    is CheckDataResult.Success -> {
                        result.data.let { films ->
                            _state.update { it.copy(films = films, isRefreshing = false) }
                        }
                    }

                    is CheckDataResult.Error -> {
                        _state.update { it.copy(isRefreshing = false) }
                        _snack.send(result.error)
                    }
                }
            }
            .launchIn(viewModelScope)

        settingsManager.languageFlow
            .drop(1)
            .distinctUntilChanged()
            .onEach {
                refreshFilms()
            }
            .launchIn(viewModelScope)
    }

    fun changeCategory(category: FilmCategory) {
        if (_state.value.selectedCategory == category.category) return
        _state.update { it.copy(selectedCategory = category.category) }
        loadAction.tryEmit(false)
    }

    fun refreshFilms() {
        loadAction.tryEmit(true)
    }
}