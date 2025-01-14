package com.abav.footballfranzy.APIFootball

import com.abav.footballfranzy.model.League
import com.abav.footballfranzy.model.Team

data class Event(
    val id: Int,
    val startDate: String,
    val homeTeam: Team,
    val visitingTeam: Team,
    val homeTeamScore: Int?,
    val visitingTeamScore: Int?,
    val matchTime : String,
    val league: League,
    val stadium : String
)
