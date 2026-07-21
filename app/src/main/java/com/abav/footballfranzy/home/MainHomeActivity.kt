package com.abav.footballfranzy.home

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment

import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.abav.footballfranzy.auth.AuthActivity
import com.abav.footballfranzy.APIFootball.GroupChatFragment
import com.abav.footballfranzy.APIFootball.LeaugeTableFragment
import com.abav.footballfranzy.APIFootball.MainHomeFragment
import com.abav.footballfranzy.APIFootball.UpcomingMatchesFragment
import com.abav.footballfranzy.R
import com.abav.footballfranzy.databinding.ActivityMainHomeBinding

class MainHomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainHomeBinding
    private lateinit var auth: FirebaseAuth
    private  lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        FirebaseApp.initializeApp(this)


        binding = ActivityMainHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set the toolbar as the ActionBar
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

// Initialize the ActionBarDrawerToggle
        val toggle = ActionBarDrawerToggle(
            this,
            binding.drawerLayout, // The DrawerLayout
            binding.toolbar,      // The Toolbar
            R.string.open,        // Open drawer description
            R.string.close        // Close drawer description
        )

// Add the DrawerToggle as a DrawerListener to the DrawerLayout
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState() // Sync the drawer toggle state

        // initialize firebase auth object.
        auth= FirebaseAuth.getInstance()
        // replace or show the  home fragment
        replaceFragment(MainHomeFragment())


        binding.navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.home -> {
                    replaceFragment(MainHomeFragment())
                }

                R.id.upcomingMatches -> {
                    replaceFragment(UpcomingMatchesFragment())
                    binding.drawerLayout.closeDrawer(GravityCompat.START)
                }
                R.id.leaugeTable -> {
                    replaceFragment(LeaugeTableFragment())
                    binding.drawerLayout.closeDrawer(GravityCompat.START)
                }
                R.id.groupChat -> {
                    replaceFragment(GroupChatFragment())
                    binding.drawerLayout.closeDrawer(GravityCompat.START)
                }
                R.id.logout -> {
                    auth.signOut()
                    val intent = Intent(this, AuthActivity::class.java)
                    startActivity(intent)
                    finishAffinity()
                }
            }
            binding.drawerLayout.closeDrawers()
            true
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }

    // override the onBackPressed() function to close the Drawer when the back button is clicked
    override fun onBackPressed() {
        if (this.binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            this.binding.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)){
            true
        }
        return super.onOptionsItemSelected(item)
    }

    }
