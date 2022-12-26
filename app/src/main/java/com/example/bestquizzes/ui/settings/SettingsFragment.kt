package com.example.bestquizzes.ui.settings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import com.example.bestquizzes.R
import com.example.bestquizzes.databinding.FragmentPersonBinding
import com.example.bestquizzes.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {

    private lateinit var binding: FragmentSettingsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        binding = FragmentSettingsBinding.inflate(layoutInflater)

        binding.updateTheme.setOnClickListener {
            themeAlertDialog(it)
        }
        binding.notification.setOnClickListener {
            notificationAlertDialog(it)
        }

        return binding.root
    }

    private fun themeAlertDialog(view: View) {
        val builder = AlertDialog.Builder(view.context)
        builder.setTitle("Тёмная тема")
        builder.setNeutralButton("Включить") { _, _ ->

        }
        builder.setNegativeButton("Выключить") { _, _ ->

        }
        builder.show()
    }

    private fun notificationAlertDialog(view: View) {
        val builder = AlertDialog.Builder(view.context)
        builder.setNeutralButton("Включить") { _, _ ->

        }
        builder.setNegativeButton("Выключить") { _, _ ->

        }
        builder.show()
    }

}