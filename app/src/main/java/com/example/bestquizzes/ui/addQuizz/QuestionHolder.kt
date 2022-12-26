package com.example.bestquizzes.ui.addQuizz

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bestquizzes.R


class QuestionHolder(inflater: LayoutInflater, parent: ViewGroup):RecyclerView.ViewHolder(inflater.inflate(
    R.layout.list_item_questions, parent, false)) {
    private val context: Context = parent.context
    private val deleteBtn: ImageView = itemView.findViewById(R.id.deleteQuest)
    private val questNumber: TextView = itemView.findViewById(R.id.question_number)
    init {
        deleteBtn.setOnClickListener{
            //TODO удалеие вопроса
        }
    }
    fun set(number: Int){
        questNumber.text = context.getString(R.string.quest_number,number)
    }
}