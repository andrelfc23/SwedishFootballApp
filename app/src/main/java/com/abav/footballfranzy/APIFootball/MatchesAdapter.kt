package com.abav.footballfranzy.APIFootball

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView


import com.bumptech.glide.Glide
import com.abav.footballfranzy.databinding.ItemMatchBinding

class MatchesAdapter : ListAdapter<Event, MatchesAdapter.MatchViewHolder>(MatchDiffCallback()) {

    private var fullList : List<Event> = emptyList()

    fun submitFullList(list: List<Event>) {
        fullList = list
        println("Full list received, size: ${fullList.size}")
        submitList(list)
    }



    fun filter(query: String) {
        val filteredList = if (query.isEmpty()) {
            fullList
        } else {
            fullList.filter { event: Event ->
                event.homeTeam.name?.contains(query, ignoreCase = true) == true ||
                        event.visitingTeam.name?.contains(query, ignoreCase = true) == true ||
                        event.stadium?.contains(query, ignoreCase = true) == true
            }
        }

        println("Filtered list size: ${filteredList.size} for query: $query") // Debug-logg
        submitList(filteredList)
    }

    override fun submitList(list: List<Event>?){
        super.submitList(list)
        notifyDataSetChanged()
    }



    class MatchViewHolder(private val binding: ItemMatchBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(event: Event) {
            binding.homeTeamName.text = event.homeTeam.name
            binding.visitingTeamName.text = event.visitingTeam.name
            binding.matchTime.text = event.matchTime

            // Sätt arenan och ligan
            binding.arenaName.text = event.homeTeam.arena?.name // Korrekt fält för arena
            binding.leagueName.text = event.league.name

            // Ladda logotyper
            //Används för att ladda bilder från nätet
            Glide.with(binding.homeTeamLogo.context)
                .load(event.homeTeam.logo)
                .into(binding.homeTeamLogo)

            Glide.with(binding.visitingTeamLogo.context)
                .load(event.visitingTeam.logo)
                .into(binding.visitingTeamLogo)

            // Om matchresultat finns
            if (event.homeTeamScore != null && event.visitingTeamScore != null) {
                binding.matchTime.text = "${event.homeTeamScore} - ${event.visitingTeamScore}"
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchViewHolder {
        val binding = ItemMatchBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MatchViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MatchViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class MatchDiffCallback : DiffUtil.ItemCallback<Event>() {
    override fun areItemsTheSame(oldItem: Event, newItem: Event): Boolean {
        return oldItem.id == newItem.id // Jämför med unikt ID
    }

    override fun areContentsTheSame(oldItem: Event, newItem: Event): Boolean {
        return oldItem == newItem // Jämför hela objektet
    }
}
