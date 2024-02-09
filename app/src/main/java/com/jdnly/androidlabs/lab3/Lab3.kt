package com.jdnly.androidlabs.lab3

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.jdnly.androidlabs.R
import kotlin.random.Random

class Lab3 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lab3)

        if (savedInstanceState != null) {
            val currentList = ArrayList(savedInstanceState.getIntArray("myArray")!!.toList())
            for (i in 0 until currentList.size)
                addTextView(currentList[i])
            findViewById<ScrollView>(R.id.scrollView).scrollY = savedInstanceState.getInt("scrollPosition")
        }
    }

    private var scrollPosition = 0
    private var list = ArrayList<Int>()

    fun buttonAddClick(view : View) {
        addTextView(Random.nextInt(0, 100))
        setScrollPosition()
    }

    fun addTextView(number : Int) {
        val textView = TextView(this)
        list.add(number)
        textView.text = number.toString()
        textView.textSize = 24f
        val container = findViewById<LinearLayout>(R.id.innerContainer)
        container.addView(textView)
    }

    fun setScrollPosition()
    {
        scrollPosition = findViewById<ScrollView>(R.id.scrollView).scrollY
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("scrollPosition", scrollPosition)
        outState.putIntArray("myArray", list.toIntArray())
    }
}