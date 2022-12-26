package com.example.bestquizzes.ui

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.bestquizzes.R
import com.example.bestquizzes.databinding.ActivityRegistrationBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class Registration : AppCompatActivity() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    private lateinit var binding: ActivityRegistrationBinding
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnRegR.setOnClickListener {


            binding.textPassword.text = ""
            binding.textEmail.text = ""
            binding.textPassword2.text = ""

            if (binding.NameR.text.toString().isEmpty() || binding.emailR.text.toString()
                    .isEmpty() || binding.passwordR.text.toString().isEmpty() || binding.passwordR2.text.toString().isEmpty()
            ) {
                Toast.makeText(this, "Пожалуйста, заполните все поля", Toast.LENGTH_SHORT).show()
            }
            else if (binding.NameR.text.toString().length < 8 || binding.NameR.text.toString().length > 16) {
                binding.textEmail.text = resources.getString(R.string.check_nik_name)
            }
            else if (!Patterns.EMAIL_ADDRESS.matcher(binding.emailR.text.toString()).matches()) {
                binding.textEmail.text = resources.getString(R.string.valid_email)
            }
            else if (binding.passwordR.text.toString().length < 8 ||
                !binding.passwordR.text.toString().matches(".*[A-Z].*".toRegex()) ||
                !binding.passwordR.text.toString().matches(".*[a-z].*".toRegex()) ||
                !binding.passwordR.text.toString().matches(".*[0-9].*".toRegex()) ||
                !binding.passwordR.text.toString().matches(".*[@#\$%^_+=].*".toRegex())
            ) {
                binding.textPassword.text = resources.getString(R.string.valid_pass)
            }
            else if (binding.passwordR.text.toString() != binding.passwordR2.text.toString()){
               binding.textPassword2.text = resources.getString(R.string.check_pass)
            }
            else {
                auth.createUserWithEmailAndPassword(
                    binding.emailR.text.toString(),
                    binding.passwordR.text.toString()
                )
                    .addOnCompleteListener(this) { taskAuth ->
                        if (taskAuth.isSuccessful) {
                            userNickName(taskAuth.result.user?.uid)
                        } else {
                            Toast.makeText(this, "Пользователь с такой почтой зарегиситрирован!", Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }
    }

    private fun userNickName(uid: String?) {
        databaseReference = Firebase.database.reference
        val nikName = binding.NameR.text.toString()
        val user = User(nikName)
        if(uid != null){
            databaseReference.child("Users").child(uid).setValue(user).addOnCompleteListener { taskName ->
                if (taskName.isSuccessful){
                    Toast.makeText(this, "Регистрация пройдена успешно!", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, Authorization::class.java))
                }else{
                    Toast.makeText(this, "Пользователь с таким псевдонимом зарегистрирован!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}