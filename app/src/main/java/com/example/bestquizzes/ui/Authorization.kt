package com.example.bestquizzes.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.bestquizzes.databinding.ActivityAuthorizationBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth

class Authorization : AppCompatActivity() {

    private lateinit var binding: ActivityAuthorizationBinding
    private val aAuth: FirebaseAuth = FirebaseAuth.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthorizationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnReg.setOnClickListener {
            startActivity(Intent(this, Registration::class.java))
        }

        binding.btnEnter.setOnClickListener{
            if (binding.email.text.toString().isEmpty() || binding.password.text.toString().isEmpty()){
                Toast.makeText(this, "Поля не должны быть пустыми", Toast.LENGTH_SHORT).show()
            }
            else{
                aAuth.signInWithEmailAndPassword(binding.email.text.toString(), binding.password.text.toString())
                    .addOnCompleteListener(this){task ->
                        if (task.isSuccessful){
                            startActivity(Intent(this, MainActivity::class.java))

                        }
                        else{
                            Toast.makeText(this, "Пользователь не найден", Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }

        binding.btnGuest.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

    }

}