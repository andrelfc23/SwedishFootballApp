package com.abav.footballfranzy.model

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class ResponseLeagueTable(

	@field:SerializedName("availableGroupTypes")
	val availableGroupTypes: List<String?>? = null,

	@field:SerializedName("league")
	val league: League? = null,

	@field:SerializedName("groups")
	val groups: List<GroupsItem?>? = null
) : Parcelable

@Parcelize
data class Logos(

	@field:SerializedName("small")
	val small: String? = null
) : Parcelable

@Parcelize
data class Season(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("startYear")
	val startYear: Int? = null,

	@field:SerializedName("endYear")
	val endYear: Int? = null,

	@field:SerializedName("slug")
	val slug: String? = null
) : Parcelable

@Parcelize
data class Team(

	@field:SerializedName("articleName")
	val articleName: String? = null,

	@field:SerializedName("teamClass")
	val teamClass: String? = null,

	@field:SerializedName("municipality")
	val municipality: Municipality? = null,

	@field:SerializedName("arena")
	val arena: Arena? = null,

	@field:SerializedName("abbreviation")
	val abbreviation: String? = null,

	@field:SerializedName("logos")
	val logos: Logos? = null,

	@field:SerializedName("teamClassId")
	val teamClassId: Int? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("logo")
	val logo: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("shortName")
	val shortName: String? = null,

	@field:SerializedName("articleShortName")
	val articleShortName: String? = null,

	@field:SerializedName("sport")
	val sport: Sport? = null
) : Parcelable

@Parcelize
data class Arena(

	@field:SerializedName("articleName")
	val articleName: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("position")
	val position: Position? = null,

	@field:SerializedName("rt90coordinates")
	val rt90coordinates: Rt90coordinates? = null,

	@field:SerializedName("city")
	val city: String? = null
) : Parcelable

@Parcelize
data class Rt90coordinates(

	@field:SerializedName("e")
	val e: Int? = null,

	@field:SerializedName("n")
	val n: Int? = null
) : Parcelable

@Parcelize
data class GroupsItem(

	@field:SerializedName("standings")
	val standings: List<StandingsItem?>? = null
) : Parcelable

@Parcelize
data class Municipality(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: Int? = null
) : Parcelable

@Parcelize
data class StatsItem(

	@field:SerializedName("groupType")
	val groupType: String? = null,

	@field:SerializedName("primarySorting")
	val primarySorting: Boolean? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("label")
	val label: String? = null,

	@field:SerializedName("value")
	val value: String? = null
) : Parcelable

@Parcelize
data class PositionStatusesItem(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("type")
	val type: String? = null,

	@field:SerializedName("destinationLeagueName")
	val destinationLeagueName: String? = null
) : Parcelable

@Parcelize
data class League(

	@field:SerializedName("teamClass")
	val teamClass: String? = null,

	@field:SerializedName("level")
	val level: String? = null,

	@field:SerializedName("endDate")
	val endDate: String? = null,

	@field:SerializedName("standings")
	val standings: Boolean? = null,

	@field:SerializedName("immutableId")
	val immutableId: Int? = null,

	@field:SerializedName("teamClassId")
	val teamClassId: Int? = null,

	@field:SerializedName("hasSubLeagues")
	val hasSubLeagues: Boolean? = null,

	@field:SerializedName("levelId")
	val levelId: Int? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("season")
	val season: Season? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("sport")
	val sport: Sport? = null,

	@field:SerializedName("startDate")
	val startDate: String? = null
) : Parcelable

@Parcelize
data class StandingsItem(

	@field:SerializedName("stats")
	val stats: List<StatsItem?>? = null,

	@field:SerializedName("positionStatuses")
	val positionStatuses: List<PositionStatusesItem?>? = null,

	@field:SerializedName("team")
	val team: Team? = null,

	@field:SerializedName("position")
	val position: Int? = null,

	@field:SerializedName("lineThicknessBelow")
	val lineThicknessBelow: Int? = null
) : Parcelable

@Parcelize
data class Position(

	@field:SerializedName("latitude")
	val latitude: String? = null,

	@field:SerializedName("longitude")
	val longitude: String? = null
) : Parcelable

@Parcelize
data class Sport(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("slug")
	val slug: String? = null
) : Parcelable
