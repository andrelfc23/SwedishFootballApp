package com.abav.footballfranzy.APIFootball

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.abav.footballfranzy.R
import com.bumptech.glide.Glide
import com.abav.footballfranzy.model.StandingsItem

class LeagueTableAdapter(
    private val standings: List<StandingsItem>
) : RecyclerView.Adapter<LeagueTableAdapter.LeagueViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LeagueViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_league_standings, parent, false)
        return LeagueViewHolder(view)
    }

    override fun onBindViewHolder(holder: LeagueViewHolder, position: Int) {
        holder.bind(position + 1, standings[position])
    }

    override fun getItemCount(): Int = standings.size

    class LeagueViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvSrNo: TextView = itemView.findViewById(R.id.tvSrNo)
        private val tvTeamName: TextView = itemView.findViewById(R.id.tvTeamName)
        private val ivTeamLogo: ImageView = itemView.findViewById(R.id.ivTeamLogo)
        private val tvM: TextView = itemView.findViewById(R.id.tvM)
        private val tvV: TextView = itemView.findViewById(R.id.tvV)
        private val tvO: TextView = itemView.findViewById(R.id.tvO)
        private val tvF: TextView = itemView.findViewById(R.id.tvF)
        private val tvP: TextView = itemView.findViewById(R.id.tvP)

        fun bind(srNo: Int, standing: StandingsItem) {
            tvSrNo.text = srNo.toString()
            tvTeamName.text = standing.team?.shortName ?: "-"
            Glide.with(itemView.context)
                .load(standing.team?.logo)
                .placeholder(R.drawable.football) // Add a placeholder image if needed
                .error(R.drawable.football) // Add an error image if needed
                .into(ivTeamLogo)

            // Set stats values or fallback to "-"
            tvM.text = standing.stats?.find { it?.label == "M" }?.value ?: "-"
            tvV.text = standing.stats?.find { it?.label == "V" }?.value ?: "-"
            tvO.text = standing.stats?.find { it?.label == "O" }?.value ?: "-"
            tvF.text = standing.stats?.find { it?.label == "F" }?.value ?: "-"
            tvP.text = standing.stats?.find { it?.label == "P" }?.value ?: "-"
        }
    }
}
