package com.abav.footballfranzy.APIFootball


import com.abav.footballfranzy.model.ResponseLeagueTable
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.Call


interface MatchesAPI {

    @GET("leagues/{leagueId}/standings")
    fun getLeagueStandings(
        @Path("leagueId") leagueId: Int,
        @Query("apikey") apiKey: String
    ): Call<ResponseLeagueTable>

    @GET("events?apikey=26192887ec48f76ab54167238ae16688")
    suspend fun getUpcomingMatches(): Response<MatchesResponse>
}
