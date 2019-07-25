package com.dvm.appd.bosm.dbg

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.dvm.appd.bosm.dbg.events.view.EventsFragment
import com.dvm.appd.bosm.dbg.wallet.views.fragments.StallsFragment
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {

    private lateinit var bottomNav: BottomNavigationView

    private val navListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->

        var selectedFragment: Fragment
        when (item.itemId) {
           R.id.action_events -> {
                selectedFragment = EventsFragment()
                supportFragmentManager.beginTransaction().replace(R.id.container, selectedFragment).commit()
               Toast.makeText(this,"events",Toast.LENGTH_LONG).show()
                return@OnNavigationItemSelectedListener true
            }
            R.id.action_food -> {
                selectedFragment = StallsFragment()
                supportFragmentManager.beginTransaction().replace(R.id.container, selectedFragment).addToBackStack(null).commit()
                //Toast.makeText(this,"food",Toast.LENGTH_LONG).show()
                return@OnNavigationItemSelectedListener true
            }
            R.id.action_order_history -> {
                selectedFragment = EventsFragment()
                supportFragmentManager.beginTransaction().replace(R.id.container, selectedFragment).addToBackStack(null).commit()
                Toast.makeText(this,"orderhistory",Toast.LENGTH_LONG).show()
                return@OnNavigationItemSelectedListener true
            }
            R.id.action_game -> {
                selectedFragment = EventsFragment()
                supportFragmentManager.beginTransaction().replace(R.id.container, selectedFragment).addToBackStack(null).commit()
                Toast.makeText(this,"game",Toast.LENGTH_LONG).show()
                return@OnNavigationItemSelectedListener true
            }
            R.id.action_more-> {
                selectedFragment = EventsFragment()
                supportFragmentManager.beginTransaction().replace(R.id.container, selectedFragment).addToBackStack(null).commit()
                Toast.makeText(this,"more",Toast.LENGTH_LONG).show()
                return@OnNavigationItemSelectedListener true
            }
        }
        false


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.actionbaritems,menu)
        return super.onCreateOptionsMenu(menu)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.my_toolbar))
        bottomNav = findViewById(R.id.bottom_navigation_bar)
        bottomNav.setOnNavigationItemSelectedListener(navListener)

        supportFragmentManager.beginTransaction().replace(R.id.container,
            EventsFragment()
        ).commit()
        bottomNav.selectedItemId = R.id.action_events


    }



    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_profile -> {

            // Open Profile Fragment with hidden bottom nav and toolbar
            Toast.makeText(this,"Profile Fragment",Toast.LENGTH_LONG).show()
            true
        }

        R.id.action_cart -> {

            // Open Cart Fragment with hidden bottom nav and toolbar
            Toast.makeText(this,"Cart Fragment",Toast.LENGTH_LONG).show()
            true
        }

        R.id.action_notifications -> {

            // Open Notifications Fragment with hidden bottom nav and toolbar
            Toast.makeText(this,"Notifications Fragment",Toast.LENGTH_LONG).show()
            true
        }

        else -> {
            // If we got here, the user's action was not recognized.
            // Invoke the superclass to handle it.
            super.onOptionsItemSelected(item)
        }
    }
}
