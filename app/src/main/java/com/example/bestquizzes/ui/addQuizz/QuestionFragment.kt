package com.example.bestquizzes.ui.addQuizz

import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bestquizzes.R
import com.example.bestquizzes.databinding.FragmentQuestionBinding
import com.example.bestquizzes.models.Question
import com.example.bestquizzes.models.Test
import com.example.bestquizzes.ui.MainActivity
import com.example.bestquizzes.ui.addQuizz.adapters.QuestionAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class QuestionFragment : Fragment() {

    private var test: Test? = null
    private lateinit var databaseReference: DatabaseReference
    private var model: Parcelable? = null
    private lateinit var questionAdapter: QuestionAdapter
    private lateinit var binding: FragmentQuestionBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentQuestionBinding.inflate(layoutInflater)

        val bundle = this.arguments
        if (bundle != null) {
            model = bundle.getParcelable("Test")
        }

        configureAdapter()
        questionAdapter.setItems(listOf(Question(1, "выаываы", 1, "выаываы", "выаываы", "выаываы", "выаываы")))

        binding.addQuest.setOnClickListener{
            val fragment = WriteQuestion()
            val bundle1 = Bundle()
            bundle1.putParcelable("Test", model)
            fragment.arguments = bundle
            val transaction = fragmentManager?.beginTransaction()
            transaction?.replace(R.id.fragment_container, fragment)?.commit()
        }

        return binding.root
    }

    private fun configureAdapter(){
        binding.questionRecycler.layoutManager = LinearLayoutManager(requireContext())
        questionAdapter = QuestionAdapter()
        binding.questionRecycler.adapter = questionAdapter
    }

//    private fun getTest(){
//        val uid = MainActivity().uid
//        databaseReference = Firebase.database.reference
//        if(uid != null){
//            test = databaseReference.child("Users").child(uid).get().result.value
//        }
//    }
}