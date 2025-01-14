package com.abav.footballfranzy.APIFootball

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import androidx.lifecycle.MutableLiveData


class MatchesViewModel(private val repository: MatchesRepository) : ViewModel() {

    private val _matchesLiveData = MutableLiveData<List<Event>>()
    val matchesLiveData: LiveData<List<Event>> get() = _matchesLiveData

    fun fetchUpcomingMatches() {
        if (_matchesLiveData.value.isNullOrEmpty()) {
            viewModelScope.launch {
                try {
                    val matches = repository.getUpcomingMatches()
                    _matchesLiveData.postValue(matches ?: emptyList())
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }


    fun getFullMatchesList() : List<Event>{
        return _matchesLiveData.value ?: emptyList()

    }

}


