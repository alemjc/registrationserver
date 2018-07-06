package com.apps.alemjc.gymdemo

import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.graphics.Color
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_workout.*
import kotlin.collections.ArrayList

class WorkoutActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        val mViewAdapter: RecyclerView.Adapter<*>
        val mLayoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
        val intent = Intent(this, MapsActivity::class.java)

        val itemDecor = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)

        val bodyParts = java.util.ArrayList<String>(resources.getStringArray(R.array.bodyparts).asList())

        mViewAdapter = MyViewAdapter(this, bodyParts)


        my_recycler_view.apply {
            adapter = mViewAdapter
            layoutManager = mLayoutManager
            addItemDecoration(itemDecor)
        }

        findViewById<Button>(R.id.submit).setOnClickListener{
            _ -> startActivity(intent)
        }


    }


    class MyViewAdapter(private val ctx: Context, private val myDataSet: ArrayList<String>):
            RecyclerView.Adapter<MyViewAdapter.ViewHolder> (){

        private val selected = HashSet<String>()
        class ViewHolder(val view: TextView): RecyclerView.ViewHolder(view)

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.view.text = myDataSet[position]

            holder.view.setOnClickListener {
                v: View? ->
                    if (selected.contains(v.toString())) {
                        selected.remove(v.toString())
                        v!!.setBackgroundColor(Color.TRANSPARENT)
                        (v as TextView).setTextColor(ContextCompat.getColor(ctx, R.color.secondaryText))
                    } else {
                        selected.add(v.toString())
                        v!!.setBackgroundColor(ctx.getColor(R.color.colorAccent))
                        (v as TextView).setTextColor(ContextCompat.getColor(ctx, R.color.textIcons))
                    }
            }

        }

        override fun getItemCount(): Int {
            return myDataSet.size
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

            val textView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.workout_selection, parent, false) as TextView

            return ViewHolder(textView)
        }
    }

}
