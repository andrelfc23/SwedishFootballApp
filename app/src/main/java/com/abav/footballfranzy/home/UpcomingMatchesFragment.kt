package com.abav.footballfranzy.APIFootball


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.abav.footballfranzy.BuildConfig
import com.abav.footballfranzy.databinding.FragmentUpcomingMatchesBinding


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [UpcomingMatchesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class UpcomingMatchesFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null


    private var _binding: FragmentUpcomingMatchesBinding? = null
    private val binding get() = _binding!!


    private lateinit var viewModel : MatchesViewModel
    private lateinit var adapter : MatchesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUpcomingMatchesBinding.inflate(inflater, container, false)

        requireActivity().title = "Matches"

        // Skapa ViewModel och Repository
        val repository = MatchesRepository(RetrofitClient.api)
        viewModel = MatchesViewModel(repository)

        // Ställ in RecyclerView
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        adapter = MatchesAdapter()
        binding.recyclerView.adapter = adapter

        // Observera live data
        viewModel.matchesLiveData.observe(viewLifecycleOwner, Observer { matches ->
            binding.progressBar.visibility = View.GONE
            if (matches.isNullOrEmpty()) {
                binding.emptyView.visibility = View.VISIBLE
            } else {
                binding.emptyView.visibility = View.GONE
                Log.d("Matchesss", matches.toString())
                adapter.submitFullList(matches) // Skicka hela listan till adaptern
            }
        })


        binding.searchView.visibility = View.VISIBLE

        binding.searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    adapter.filter(it)
                    println("Query text submitted: $it")
                adapter.filter(it)// Debug-logg
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    adapter.filter(it)
                    println("Query text changed: $it")
                adapter.filter(it)// Debug-logg
                }
                return true
            }
        })



        // Hämta data från API:t
        viewModel.fetchUpcomingMatches(BuildConfig.EVERYSPORT_API_KEY)

        return binding.root
    }



}