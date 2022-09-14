package com.example.simpletodo

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.apache.commons.io.FileUtils
import java.io.File
import java.io.IOException
import java.nio.charset.Charset
import java.text.FieldPosition

class MainActivity : AppCompatActivity() {

    var listOfTasks = mutableListOf<String>()
    lateinit var adapter: TaskItemAdapter
    var helperPosition: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //items can be removed by long clicking
        val onLongClickListener = object : TaskItemAdapter.OnLongClickListener{
            override fun onItemLongClicked(position: Int) {
                //remove item from the list
                listOfTasks.removeAt(position)
                // notify the adapter that something has changed
                adapter.notifyDataSetChanged()

                saveItems()
            }
        }
        fun showEditTextDialog(position: Int) {
            val builder = AlertDialog.Builder(this)
            val inflater = layoutInflater
            val dialogLayout = inflater.inflate(R.layout.edit_text_layout,null)
            val editText = dialogLayout.findViewById<EditText>(R.id.et_editText)

            with(builder) {
                setTitle("Edit task " + (position+1))
                setPositiveButton("Save"){dialog, which ->
                    listOfTasks.set(position,editText.text.toString())
                    adapter.notifyItemChanged(position)
                }
                setNegativeButton("Cancel"){dialog, which ->
                    Log.i("Pepper","Negative Button Pressed")
                }
                setView(dialogLayout)
                show()
            }
        }
        //items can be edited by clicking
        val onClickListener = object : TaskItemAdapter.onClickListener {
            override fun onItemClicked(position: Int) {
                 showEditTextDialog(position)
            }
        }


        //populate list based on file
        loadItems()

        //Detect when User presses Add button
        /*
        findViewById<Button>(R.id.button).setOnClickListener {
            //code within the curly braces are executed when a button is pressed
            Log.i("Pepper","Click!")
        }
        */
        //hard-coded example
        //listOfTasks.add("First activity")
        //listOfTasks.add("Third activity")
        //Look up recyclerView in layout
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        //create adapter
        adapter = TaskItemAdapter(listOfTasks, onLongClickListener, onClickListener)
        //attach view and adapter
        recyclerView.adapter = adapter
        //set layout manager
        recyclerView.layoutManager = LinearLayoutManager(this)

        //setup button and input functionality so the user can add a task to the recycler view
        val inputTextField = findViewById<EditText>(R.id.addTaskField)
        findViewById<Button>(R.id.button).setOnClickListener {
            //get text from edit text field
            val userInputtedTask = inputTextField.text.toString()
            //add string to list of tasks: listOfTasks
            listOfTasks.add(userInputtedTask)
            //notify the adapter to know that data has been updated
            adapter.notifyItemInserted(listOfTasks.size - 1)
            //clear text field
            inputTextField.setText("")
            //save new tasks to file
            saveItems()
        }
    }
    //save the data that the user has inputted using a file

    //create a method to get the file
    fun getdataFile() : File {
        //every line represents 1 task in this list of tasks
        return File(filesDir, "tasks.txt")
    }
    //load items by reading from the file
    fun loadItems() {
        try {
        listOfTasks = FileUtils.readLines(getdataFile(), Charset.defaultCharset())
        } catch (ioException: IOException) {
            ioException.printStackTrace()
        }
    }
    //save items by writing to the file
    fun saveItems() {
        try {
            FileUtils.writeLines(getdataFile(), listOfTasks)
        } catch (ioException: IOException) {
            ioException.printStackTrace()
        }
    }
}