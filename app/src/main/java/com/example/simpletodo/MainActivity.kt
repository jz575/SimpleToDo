package com.example.simpletodo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    val listOfTasks = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Detect when User presses Add button
        /*
        findViewById<Button>(R.id.button).setOnClickListener {
            //code within the curly braces are executed when a button is pressed
            Log.i("Pepper","Click!")
        }
        */
        //hard-coded example
        listOfTasks.add("First activity")
        listOfTasks.add("Third activity")
        //Look up recyclerView in layout
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        //create adapter
        val adapter = TaskItemAdapter(listOfTasks)
        //attach view and adapter
        recyclerView.adapter = adapter
        //set layout manager
        recyclerView.layoutManager = LinearLayoutManager(this)
    }
}