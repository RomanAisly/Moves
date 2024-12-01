package com.moves.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moves.domain.model.Films
import com.moves.domain.model.FilmsRepositoryImpl
import com.moves.utils.ResultData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(private val repositoryImpl: FilmsRepositoryImpl) :
    ViewModel() {

    private val _allFilms = MutableStateFlow<List<Films>>(emptyList())
    val allFilms = _allFilms.asStateFlow()

    private val _toast = Channel<Boolean>()
    val toast = _toast.receiveAsFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repositoryImpl.getFilms(page = 2, forceFetch = false)
                .collectLatest { result ->
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