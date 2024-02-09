package com.jdnly.androidlabs.lab4

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.CheckedTextView
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import com.jdnly.androidlabs.R

class Lab4 : AppCompatActivity() {
    lateinit var equation : Equation
    var bIsAnswerSelected : Boolean = false
    var answer : Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lab4)

        val answersList : ListView = findViewById(R.id.listView)

        answersList.choiceMode = ListView.CHOICE_MODE_SINGLE
        answersList.setOnItemClickListener { parent, view, position, id ->
            bIsAnswerSelected = true
            answer = view.findViewById<CheckedTextView>(android.R.id.text1).text.toString().toInt()
        }

        val equationString : TextView = findViewById(R.id.equation)
        equation = Equation(this, equationString, answersList)
        equation.setAnswersList()
        updateCounter()
    }

    fun onAnswerButtonClicked(view: View) {
        val toast: Toast
        if (!bIsAnswerSelected) {
            toast = Toast.makeText(this,"Select the answer!", Toast.LENGTH_SHORT)
        }
        else if (equation.isAnswerCorrect(answer)) {
            toast = Toast.makeText(this,"Correct!", Toast.LENGTH_SHORT)
            equation.setAnswersList()
        } else {
            toast = Toast.makeText(this,"No!", Toast.LENGTH_SHORT)
            equation.setAnswersList()
        }
        toast.show()
        updateCounter()
        bIsAnswerSelected = false
    }

    private fun updateCounter() {
        val statisticsText: TextView = findViewById(R.id.counter)
        val resText = resources.getString(R.string.statistics)
        statisticsText.text = String.format(resText,
            equation.correctAnswersAmount, equation.answersAmount)
    }
}
