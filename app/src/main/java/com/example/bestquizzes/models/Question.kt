package com.example.bestquizzes.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Question (
    var number: Int,
    var question: String,
    var answer: Int,
    var firstVariant: String,
    var secondVariant: String,
    var thirdVariant: String? = null,
    var fourthVariant: String? = null
):Parcelable