package com.apps.alemjc.gymdemo

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.GridLayout
import android.widget.TextView

import kotlinx.android.synthetic.main.activity_trainers.*
import kotlinx.android.synthetic.main.content_trainers.*
import java.text.SimpleDateFormat
import java.util.*

class TrainersActivity : AppCompatActivity() {

    companion object {
        const val FOURTY_MINUTES_TO_MILLISECONDS = 2400000
        const val TEN_MINUTES_TO_MILLISECONDS = 600000
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trainers)
        setSupportActionBar(toolbar)
        val twoHoursInMilli = 7200000
//      For testing
        val trainersList = listOf(Trainer("Robin", "I love working out and I am 24 years old", "Chest/Biceps", Date(), Date(Date().time + twoHoursInMilli)),
                                    Trainer("Josiah", "I think I am fun and hyperactive", "Legs", Date(), Date(Date().time+twoHoursInMilli)))

        val recyclerAdapter = MyRecyclerAdapter(this, trainersList)

        trainers.adapter = recyclerAdapter
    }

    private class MyRecyclerAdapter(val ctx: Context, val trainers: List<Trainer>): RecyclerView.Adapter<MyRecyclerAdapter.ViewHolder>() {

        class ViewHolder(val view: View): RecyclerView.ViewHolder(view)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.trainer_profile, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {

            val trainer = trainers[position]
            val name = holder.view.findViewById(R.id.name) as TextView
            val about = holder.view.findViewById(R.id.aboutme) as TextView
            val availabilityTable = holder.view.findViewById(R.id.availability) as GridLayout
            val intent = Intent()//TODO: link this intent to the next activity

            name.text = trainer.name
            about.text = trainer.aboutMe

            var currentTime = Date(trainer.strtAvailableTime.time)

            while (currentTime.before(trainer.endAvailableTime)) {

                val tempEndTime = Date(currentTime.time + FOURTY_MINUTES_TO_MILLISECONDS)

                val smplDateFmt = SimpleDateFormat("HH.mm", Locale.US)
                val buttonView = Button(ctx)
                val startTimeStr = smplDateFmt.format(currentTime)
                val endTimeStr = smplDateFmt.format(tempEndTime)

                buttonView.text = "%s - %s".format(startTimeStr, endTimeStr)

                buttonView.setOnClickListener {
                    val bundle = Bundle()
                    bundle.putLong("startTime", currentTime.time)
                    bundle.putLong("endTime", tempEndTime.time)

                    ctx.startActivity(intent, bundle)
                }

                availabilityTable.addView(buttonView, ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT))

                currentTime = Date(tempEndTime.time + TEN_MINUTES_TO_MILLISECONDS)

            }
        }

        override fun getItemCount(): Int {
            return trainers.size
        }
    }

}
