package com.jdnly.androidlabs.lab9

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import com.jdnly.androidlabs.R
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.net.URL

class Lab9 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lab9)
    }

    private fun getSearch() : String {
        return findViewById<EditText>(R.id.search_field).text.toString()
    }

    fun loadListRepositories(view: View) {
        if (getSearch() == "") {
            Toast.makeText(view.context, "Search field is empty!", Toast.LENGTH_SHORT).show()
        } else {
            // Выполнение запросов к сети
            GlobalScope.launch {
                val str = URL("https://api.github.com/search/repositories?q=" + getSearch()).readText()
                val json = JSONObject(str)

                // Работаем с массивом объектов
                val array = json.getJSONArray("items")

                val repositories = ArrayList<String>()

                for (i in 0 until array.length()) {
                    // Получаем i-й объект в массиве
                    val obj = array.getJSONObject(i)
                    // Получаем строковое значение
                    val objName = obj.getString("name")
                    repositories.add(objName)
                }
                MainScope().launch {
                    // Вывод результатов на экран
                    val adapter = ArrayAdapter<String>(
                        view.context,
                        android.R.layout.simple_list_item_1,
                        repositories)
                    val listView: ListView = findViewById<ListView>(R.id.result_list)
                    listView.adapter = adapter

                    listView.setOnItemClickListener { adapterView: AdapterView<*>,
                                                      view1: View, i: Int, l: Long ->
                        val intent = Intent(view.context, Information::class.java)
                        intent.putExtra("repository_name", array.getJSONObject(i).getString("name"))
                        intent.putExtra("description", array.getJSONObject(i).getString("description"))
                        intent.putExtra("avatar_url", array.getJSONObject(i).getJSONObject("owner").getString("avatar_url"))
                        startActivityForResult(intent, 0)
                    }
                }
            }
        }
    }
}
