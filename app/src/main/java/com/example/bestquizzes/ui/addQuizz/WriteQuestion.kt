package com.example.bestquizzes.ui.addQuizz

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.bestquizzes.R
import com.example.bestquizzes.databinding.FragmentWriteQuestionBinding
import com.example.bestquizzes.models.Question
import com.example.bestquizzes.models.Test
import com.example.bestquizzes.ui.MainActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class WriteQuestion : Fragment() {

    private var currentUserTests: MutableList<Test> = mutableListOf()
    private var allTests: MutableList<Test> = mutableListOf()
    private val databaseReference: DatabaseReference = Firebase.database.reference
    private var test: Test? = null
    private var testFromDB: Test? = null
    private lateinit var binding: FragmentWriteQuestionBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWriteQuestionBinding.inflate(layoutInflater)
        val bundle = this.arguments
        if (bundle != null) {
            bundle.getParcelable<Test>("Test")?.let {
                test = it
            }
        }

        binding.writeQuest.setOnClickListener{
            val uid = MainActivity().uid
            if (uid != null) {
                databaseReference.child("Tests").push().setValue(saveQuest(uid))
                val fragment = QuestionFragment()
                val transaction = fragmentManager?.beginTransaction()
                transaction?.replace(R.id.fragment_container, fragment)?.commit()
            }
        }

        return binding.root
    }

    private fun saveQuest(uid: String): Test? {
        getTest(uid)
        val quest: Question = Question(
            number = binding.questionNumber.text.toString().toInt(),
            question = binding.question.text.toString(),
            answer = binding.rightAnswer.text.toString().toInt(),
            firstVariant = binding.firstAnswer.text.toString(),
            secondVariant = binding.secondAnswer.text.toString(),
            thirdVariant = binding.thirdAnswer.text.toString(),
            fourthVariant = binding.fourthAnswer.text.toString()
        )
        return if (testFromDB == null){
            test?.questionList = listOf(quest)
            test
        } else{
            testFromDB?.questionList = listOf(quest)
            testFromDB
        }
    }

    //TODO как достать тесты ?
    private fun getTest(uid: String) {
        val allTests2 = mutableListOf<Test>()
        databaseReference.child("Tests").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
//                val td: HashMap<String, Test>? = dataSnapshot.value as HashMap<String, Test>?
                dataSnapshot.children.forEach {
//                    it.getValue(Test::class.java)?.let { it1 -> allTests.add(it1) }
//                    allTests.add(it.getValue(Test::class.java)!!)
                    val test = Test()
                    val map = it.getValue() as HashMap<String, Any>
                    test.questionList = map["questionList"] as List<Question>?
                    test.nameOfTest = map["nameOfTest"] as String?
                    test.topic = map["topic"] as String?
                    test.uid = map["uid"] as String?
                    allTests2.add(test)
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                println("The read failed: " + databaseError.code)
            }
        })
        println("!!! ывыаавыаыаываывавы")

//        databaseReference.child("Tests").addListenerForSingleValueEvent(object : ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//                val children = snapshot.children
//                children.forEach {
//                    allTests.add(it.value as Test)
//                }
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                println("The read failed: " + error.code)
//            }
//        })


        for (test in allTests2) {
            if (test.uid == uid) {
                currentUserTests.add(test)
            }
        }
        testFromDB = currentUserTests.lastOrNull()
    }
}
