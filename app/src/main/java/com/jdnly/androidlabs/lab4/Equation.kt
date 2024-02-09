package com.jdnly.androidlabs.lab4

import android.content.Context
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import kotlin.random.Random

class Equation(val context: Context, val equation: TextView, val listView: ListView) {
    val numberOfEquations : Int = 4;
    var answersAmount : Int = 0
    var correctAnswersAmount: Int = 0
    var correctValue : Int = 0
    var difficultyFactor : Int = 1
    var answersAmountToChangeFactor : Int = 0
    lateinit var answers: IntArray

    private fun generateAnswers(correct: Int) : IntArray {
        val answersArray: MutableSet<Int> = mutableSetOf(correct)
        val range = createDifficultyRange(difficultyFactor)
        while (answersArray.size < numberOfEquations) {
            answersArray += Random.nextInt(range.first, range.second) + 1
        }
        return answersArray.shuffled().toIntArray()
    }

    fun setAnswersList() {
        answers = generateAnswers(generateEquation(equation, difficultyFactor))
        val adapter = ArrayAdapter<Int>(context,
            android.R.layout.simple_list_item_single_choice,
            answers.toTypedArray())
        listView.adapter = adapter
    }

    private fun generateEquation(equation: TextView, factor: Int): Int {
        val resText = context.resources.getString(com.jdnly.androidlabs.R.string.equation)
        val range = createDifficultyRange(factor)
        val first: Int = Random.nextInt(range.first, range.second)
        val second: Int = Random.nextInt(range.first, range.second)
        var sign = '+'
        when (Random.nextInt(0, 2)) {
            0 -> {
                sign = '+'
                correctValue = first + second }
            1 -> {
                sign = '-'
                correctValue = first - second }
            2 -> {
                sign = '*'
                correctValue = first * second }
        }
        equation.text = String.format(resText, getEquationString(first, second,
            sign))
        return correctValue
    }

    private fun createDifficultyRange(factor: Int): Pair<Int, Int> {
        val from: Int = -10 * factor
        val until: Int = 10 * factor
        return Pair(from, until)
    }

    private fun getEquationString(firstMember: Int, secondMember: Int, sign: Char) : String {
        val brackets = context.resources.getString(com.jdnly.androidlabs.R.string.brackets)
        var equationString : String = firstMember.toString()
        equationString += sign
        equationString += if (secondMember < 0) String.format(brackets, secondMember) else secondMember
        return equationString
    }

    fun isAnswerCorrect(answer: Int) : Boolean {
        answersAmount++
        if (answer == correctValue) {
            correctAnswersAmount++
            answersAmountToChangeFactor++
            if (answersAmountToChangeFactor == 5)
            {
                difficultyFactor++
                answersAmountToChangeFactor = 0
            }
            return true
        } else {
            answersAmountToChangeFactor = 0
            if (difficultyFactor > 1) difficultyFactor--
            return false
        }
    }
}