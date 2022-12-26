package com.example.bestquizzes.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Test (
    var nameOfTest: String? = null,
    var topic: String? = null,
    var questionList: List<Question>? = null,
    var uid: String? = null
):Parcelable
