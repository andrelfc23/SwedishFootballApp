package com.abav.footballfranzy.APIFootball

class MatchesRepository(private val api: MatchesAPI) {

    suspend fun getUpcomingMatches(apiKey: String): List<Event>? {
        val response = api.getUpcomingMatches(apiKey)
        return if (response.isSuccessful){
            response.body()?.events
        }else{
            null
        }
    }
}