package com.example.bestquizzes.ui.addQuizz

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.bestquizzes.R
import com.example.bestquizzes.databinding.FragmentAddQuizzBinding
import com.example.bestquizzes.firebase.FirebaseUser
import com.example.bestquizzes.models.Test
import com.example.bestquizzes.ui.MainActivity
import com.google.firebase.auth.FirebaseAuth


class AddQuizzFragment : Fragment() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private  lateinit var binding: FragmentAddQuizzBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddQuizzBinding.inflate(layoutInflater)
        binding.next.setOnClickListener{

            val uid = FirebaseUser.uid
            val fragment = QuestionFragment()
            val bundle = Bundle()
            if (uid != null){
                bundle.putParcelable("Test", testBuild(uid))
            }
            fragment.arguments = bundle
            val transaction = fragmentManager?.beginTransaction()
            transaction?.replace(R.id.fragment_container, fragment)?.commit()
        }
        return binding.root
    }

    private fun testBuild(uid: String): Test {
        return Test(
            nameOfTest = binding.nameQuizzes.text.toString(),
            topic = binding.topicQuizzes.text.toString(),
            uid = uid,
            questionList = mutableListOf()
        )
    }

}