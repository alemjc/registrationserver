package com.apps.alemjc.gymdemo

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckedTextView
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        var viewAdapter: RecyclerView.Adapter<*>
        var layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
        val selectedItems: ArrayList<String> = ArrayList()
        val intent = Intent(this, MapsActivity::class.java)

        val bodyParts: ArrayList<String> = resources.getStringArray(R.array.bodyparts).asList() as ArrayList<String>

        viewAdapter = MyViewAdapter(bodyParts, selectedItems)


        findViewById<RecyclerView>(R.id.my_recycler_view).apply {
            viewAdapter = viewAdapter
            setHasFixedSize(true)
            layoutManager = layoutManager
        }

        findViewById<Button>(R.id.submit).setOnClickListener{
            _ -> startActivity(intent)
        }


    }


    class MyViewAdapter(private val myDataSet: List<String>, private val selected: ArrayList<String>):
            RecyclerView.Adapter<MyViewAdapter.ViewHolder> (){
        class ViewHolder(val view: CheckedTextView): RecyclerView.ViewHolder(view)

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.view.text = myDataSet[position]

            holder.view.setOnClickListener {
                v -> selected.add(v.toString())
            }

        }

        override fun getItemCount(): Int {
            return myDataSet.size
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

            val textView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.layout, parent, false) as CheckedTextView

            return ViewHolder(textView)
        }
    }

}
