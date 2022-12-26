package com.example.bestquizzes.ui.profile

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.bestquizzes.R
import com.example.bestquizzes.databinding.ActivityRegistrationBinding
import com.example.bestquizzes.databinding.FragmentPersonBinding
import com.example.bestquizzes.ui.Authorization
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class PersonFragment : Fragment() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private lateinit var databaseReference: DatabaseReference
    private lateinit var binding: FragmentPersonBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentPersonBinding.inflate(layoutInflater)

        databaseReference = Firebase.database.reference

        val uid = auth.currentUser?.uid
        if (uid != null){
            databaseReference.child("Users").child(uid).get().addOnSuccessListener {
                binding.nickName.text = it.child("nikName").value.toString()
            }.addOnFailureListener{
                Toast.makeText(requireContext(), "Ошибка", Toast.LENGTH_SHORT).show()
            }
        }

        binding.exit.setOnClickListener {
            startActivity(Intent(requireContext(), Authorization::class.java))
        }

        return binding.root
    }

}