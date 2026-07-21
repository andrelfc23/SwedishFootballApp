package com.abav.footballfranzy.APIFootball

import android.app.ProgressDialog

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager

import com.abav.footballfranzy.BuildConfig
import com.abav.footballfranzy.R
import com.abav.footballfranzy.databinding.FragmentLeaugeTableBinding
import com.abav.footballfranzy.model.ResponseLeagueTable
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LeaugeTableFragment : Fragment() {

    private var _binding: FragmentLeaugeTableBinding?= null
    private val binding get()=  _binding
    private lateinit var progressDialog: ProgressDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //replaced inflate with binding
        _binding = FragmentLeaugeTableBinding.inflate(inflater, container, false)
        val view = binding?.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        fetchLeagueStandings()
    }

    private fun initViews() {
        // intialize dialog
        progressDialog= ProgressDialog(requireActivity())
        progressDialog.setTitle(resources.getString(R.string.app_name))
        progressDialog.setMessage("please wait")
        progressDialog.setCanceledOnTouchOutside(false)
        // showing progress
        progressDialog.show()
    }


    private fun fetchLeagueStandings() {

        android.util.Log.d("LEAGUE_DEBUG", "API Key used: '${BuildConfig.EVERYSPORT_API_KEY}'")

        val apiService = RetrofitClient.api
        apiService.getLeagueStandings(87302, BuildConfig.EVERYSPORT_API_KEY)
            .enqueue(object : Callback<ResponseLeagueTable> {
                override fun onResponse(call: Call<ResponseLeagueTable>, response: Response<ResponseLeagueTable>) {
                    android.util.Log.d("LEAGUE_DEBUG", "Code: ${response.code()}, Body: ${response.body()}")

                    if (response.isSuccessful) {

                        val leagueResponse = response.body()

                        // Ensure data is not null
                        leagueResponse?.groups?.firstOrNull()?.standings?.let { standings ->
                            val filteredStandings = standings.filterNotNull() // Remove any null items
                            // dismiss the progress
                            progressDialog.dismiss()
                            // Setup RecyclerView
                            binding?.recyclerView?.apply {
                                layoutManager = LinearLayoutManager(context)
                                adapter = LeagueTableAdapter(filteredStandings)
                            }
                        } ?: run {
                            progressDialog.dismiss()
                            Toast.makeText(context, "No standings available", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        progressDialog.dismiss()
                        Toast.makeText(context, "Error: ${response.message()}", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<ResponseLeagueTable>, t: Throwable) {
                    android.util.Log.e("LEAGUE_DEBUG", "Failure: ${t.message}", t)
                    Toast.makeText(context, "Failed: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}