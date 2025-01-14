package com.abav.footballfranzy.APIFootball

class MatchesRepository(private val api: MatchesAPI) {

    suspend fun getUpcomingMatches(): List<Event>? {
        val response = api.getUpcomingMatches()
        return if (response.isSuccessful){
            response.body()?.events
        }else{
            null
        }
    }
}