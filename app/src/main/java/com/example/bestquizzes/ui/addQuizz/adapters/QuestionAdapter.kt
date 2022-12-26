package com.example.bestquizzes.ui.addQuizz.adapters


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bestquizzes.models.Question
import com.example.bestquizzes.ui.addQuizz.QuestionHolder

class QuestionAdapter() : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var items: List<Question>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = QuestionHolder(inflater,parent)
        return view
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = items?.get(position) ?: return

        (holder as? QuestionHolder)?.set(item.number)
    }

    override fun getItemCount(): Int {
        return items?.count() ?: 0
    }

    fun setItems(items: List<Question>?){
        this.items = items
        notifyDataSetChanged()
    }
}