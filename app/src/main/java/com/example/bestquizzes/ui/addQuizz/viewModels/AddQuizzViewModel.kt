package com.example.bestquizzes.ui.addQuizz.viewModels

import androidx.lifecycle.*
import com.example.bestquizzes.firebase.FirebaseUser
import com.example.bestquizzes.models.Question
import com.example.bestquizzes.models.Test
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AddQuizzViewModel: ViewModel() {
    private val databaseReference: DatabaseReference = Firebase.database.reference
    private val uid = FirebaseUser.uid
    private var test: Test? = null

    private val _allTests: MutableLiveData<List<Test?>> = MutableLiveData(null)
    private val _lastTest = MutableLiveData<Test?>(null)
    private val _currentUserTests = MutableLiveData<List<Test?>>(null)

    val allTests: LiveData<List<Test?>>
        get() = _allTests
    val lastTest: LiveData<Test?>
        get() = _lastTest
    val currentUserTests: LiveData<List<Test?>>
        get() = _currentUserTests

    fun updateData() {
        viewModelScope.launch {
            getTest()
        }
    }

    private suspend fun getTest() = withContext(Dispatchers.IO) {
        val allTests = mutableListOf<Test>()
        databaseReference.child("Tests").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                dataSnapshot.children.forEach {
                    val test = Test()
                    val map = it.value as HashMap<String, Any>
                    test.questionList = map["questionList"] as MutableList<Question>?
                    test.nameOfTest = map["nameOfTest"] as String?
                    test.topic = map["topic"] as String?
                    test.uid = map["uid"] as String?
                    test.key = it.key
                    allTests.add(test)
                }
                _allTests.postValue(allTests)
                getLastTest()
            }

            override fun onCancelled(databaseError: DatabaseError) {
                println("The read failed: " + databaseError.code)
            }
        })
    }

    private fun getLastTest() {
        val currentUserTests = mutableListOf<Test>()
        allTests.value?.forEach { test ->
            if (test != null && test.uid == uid) {
                currentUserTests.add(test)
            }
        }
        _currentUserTests.postValue(currentUserTests)
        _lastTest.postValue(currentUserTests.lastOrNull())
    }

    fun saveTest(test: Test?) {
        this.test = test
    }

    fun saveQuestion(question: Question) {
        val test = if (lastTest.value == null) {
            test
        } else {
            lastTest.value
        }

        test?.questionList?.add(question)

        val map: MutableMap<String?, Any> = HashMap()
        if (test?.key != null) {
            map.put(test.key, test)
        }

        if (lastTest.value == null) {
            databaseReference.child("Tests").setValue(test)
        } else {
            databaseReference.child("Tests").updateChildren(map)
        }
//        databaseReference.child("Tests").updateChildren(map)
    }
}