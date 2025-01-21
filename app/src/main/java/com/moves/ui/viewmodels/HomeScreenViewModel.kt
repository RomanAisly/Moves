package com.moves.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moves.domain.model.Films
import com.moves.domain.model.FilmsRepository
import com.moves.utils.FilmsCategory
import com.moves.utils.ResultData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeScreenViewModel(private val repository: FilmsRepository) :
    ViewModel() {

    private val _allFilms = MutableStateFlow<List<Films>>(emptyList())
    val allFilms = _allFilms.asStateFlow()

    private var _category = FilmsCategory.NOW_PLAYING

    private val _toast = Channel<Boolean>()
    val toast = _toast.receiveAsFlow()

    init {
        showAllFilms()
    }

    private fun showAllFilms() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getFilms(
                page = 1,
                forceFetch = false,
                category = _category
            ).collectLatest { result ->
                when (result) {
                    is ResultData.Success -> {
                        result.data?.let { films ->
                            _allFilms.update { films }
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