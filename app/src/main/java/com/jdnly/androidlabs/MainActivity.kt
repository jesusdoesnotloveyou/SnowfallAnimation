package com.jdnly.androidlabs

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun getFirstVal() : Float {
        val edit1: EditText = findViewById(R.id.number1)
        return edit1.text.toString().toFloat()
    }

    fun getSecondVal() : Float {
        val edit2: EditText = findViewById(R.id.number2)
        return edit2.text.toString().toFloat()
    }

    fun onClickAdd(view: View) {
        val textView: TextView = findViewById(R.id.result)
        val resText = resources.getString(R.string.operation_result)
        textView.text = String.format(resText, getFirstVal() + getSecondVal())
    }

    fun onClickSubtract(view: View) {
        val textView: TextView = findViewById(R.id.result)
        val resText = resources.getString(R.string.operation_result)
        textView.text = String.format(resText, getFirstVal() - getSecondVal())
    }

    fun onClickMultiply(view: View) {
        val textView: TextView = findViewById(R.id.result)
        val resText = resources.getString(R.string.operation_result)
        textView.text = String.format(resText, getFirstVal() * getSecondVal())
    }

    fun onClickDivide(view: View) {
        val textView: TextView = findViewById(R.id.result)
        val resText = resources.getString(R.string.operation_result)
        textView.text = String.format(resText, getFirstVal() / getSecondVal())
    }
}