package com.example.bestquizzes.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.bestquizzes.R
import com.example.bestquizzes.databinding.ActivityAuthorizationBinding
import com.example.bestquizzes.databinding.ActivityMainBinding
import com.example.bestquizzes.ui.addQuizz.AddQuizzFragment
import com.example.bestquizzes.ui.filtr.FilterFragment
import com.example.bestquizzes.ui.general.GeneralFragment
import com.example.bestquizzes.ui.profile.PersonFragment
import com.example.bestquizzes.ui.settings.SettingsFragment
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    val uid = auth.currentUser?.uid
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadFragment(GeneralFragment())

        binding.bottomNavigation.setOnItemSelectedListener{ item ->

            when (item.itemId){
                R.id.homeNavigation -> {
                    loadFragment(GeneralFragment())
                    true
                }
                R.id.filterNavigation -> {
                    loadFragment(FilterFragment())
                    true
                }
                R.id.settingNavigation -> {
                    loadFragment(SettingsFragment())
                    true
                }
                R.id.addNavigation -> {
                    loadFragment(AddQuizzFragment())
                    true
                }
                R.id.personNavigation -> {
                    loadFragment(PersonFragment())
                    true
                }
                else -> false
            }

        }
    }
    private fun loadFragment(fragment: Fragment) {
        // load fragment
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthorizationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnReg.setOnClickListener {
            startActivity(Intent(this, Registration::class.java))
        }

}