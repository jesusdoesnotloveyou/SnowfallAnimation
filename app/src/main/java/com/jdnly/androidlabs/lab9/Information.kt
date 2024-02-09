package com.jdnly.androidlabs.lab9

import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.jdnly.androidlabs.R
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import java.net.URL

class Information : AppCompatActivity() {

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_lab9)

        val intent = intent

        GlobalScope.launch {
            val avatar = intent?.getStringExtra("avatar_url")
            val buf = URL(avatar).readBytes()
            val bitmap = BitmapFactory.decodeByteArray(buf, 0, buf.size);

            MainScope().launch {
                findViewById<ImageView>(R.id.avatar).setImageBitmap(bitmap)
            }
        }

        val repositoryName = intent?.getStringExtra("repository_name")
        findViewById<TextView>(R.id.name).text = repositoryName
        val description = intent?.getStringExtra("description")
        findViewById<TextView>(R.id.description).text = description

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}