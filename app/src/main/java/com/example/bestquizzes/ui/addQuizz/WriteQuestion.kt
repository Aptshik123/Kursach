package com.example.bestquizzes.ui.addQuizz

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.bestquizzes.R
import com.example.bestquizzes.databinding.FragmentWriteQuestionBinding
import com.example.bestquizzes.models.Question
import com.example.bestquizzes.models.Test
import com.example.bestquizzes.ui.addQuizz.viewModels.AddQuizzViewModel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class WriteQuestion : Fragment() {
    private lateinit var binding: FragmentWriteQuestionBinding

    private val databaseReference: DatabaseReference = Firebase.database.reference
    private val viewModel: AddQuizzViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentWriteQuestionBinding.inflate(layoutInflater)

        val bundle = this.arguments
        if (bundle != null) {
            bundle.getParcelable<Test>("Test")?.let {
                viewModel.saveTest(it)
            }
        }

        binding.writeQuest.setOnClickListener {
            viewModel.saveQuestion(createQuestion())

            val fragment = QuestionFragment()
            val transaction = fragmentManager?.beginTransaction()
            transaction?.replace(R.id.fragment_container, fragment)?.commit()
        }

        viewModel.updateData()

        return binding.root
    }

    private fun createQuestion(): Question {
        return Question(
            number = binding.questionNumber.text.toString().toInt(),
            question = binding.question.text.toString(),
            answer = binding.rightAnswer.text.toString().toInt(),
            firstVariant = binding.firstAnswer.text.toString(),
            secondVariant = binding.secondAnswer.text.toString(),
            thirdVariant = binding.thirdAnswer.text.toString(),
            fourthVariant = binding.fourthAnswer.text.toString()
        )
    }
}