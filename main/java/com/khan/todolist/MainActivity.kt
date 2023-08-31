package com.khan.todolist

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import androidx.appcompat.app.AlertDialog

class MainActivity : AppCompatActivity() {

    lateinit var button: Button
    lateinit var editText: EditText
    lateinit var list: ListView

    var itemList=ArrayList<String>()
    var fileHelper= FileHelper()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button=findViewById(R.id.button)
        editText=findViewById(R.id.editText)
        list=findViewById(R.id.list)

        itemList=fileHelper.readData(this)

        var arrayAdapter=ArrayAdapter(this,android.R.layout.simple_list_item_1,android.R.id.text1,itemList)
        list.adapter=arrayAdapter

        button.setOnClickListener {
            var itemName :String=editText.text.toString()
            itemList.add(itemName)
            editText.setText("")
            fileHelper.writeData(itemList,applicationContext)
            arrayAdapter.notifyDataSetChanged()

        }
        list.setOnItemClickListener { adapterView, view, position, l ->
            var alert= AlertDialog.Builder(this)
            alert.setTitle("Delete")
            alert.setMessage("Do you want to remove this item from the list ?")
            alert.setCancelable(false)
            alert.setNegativeButton("No",DialogInterface.OnClickListener { dialogInterface, i ->
                dialogInterface.cancel()
            })

            alert.setPositiveButton("Yes",DialogInterface.OnClickListener { dialogInterface, i ->
                itemList.removeAt(position)
                arrayAdapter.notifyDataSetChanged()
                fileHelper.writeData(itemList,applicationContext)

            })
            alert.create().show()
        }
    }
}