package com.example.covid19_patient_manager.Controller

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.example.covid19_patient_manager.Model.PatientDetailsModel
import com.example.covid19_patient_manager.R
import com.google.firebase.auth.FirebaseAuth
import java.text.SimpleDateFormat

class MainFragment : Fragment() {


    var appointmentArrayList = ArrayList<PatientDetailsModel>()

    private var listener: OnItemSelectedListener? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_main, container, false)

        val b = view.findViewById<View>(R.id.button) as Button
        b.setOnClickListener { listener!!.onButtonSelected() }
        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnItemSelectedListener) {
            listener = context
        } else {
            throw ClassCastException(context!!.toString() + " must implemenet MyListFragment.OnItemSelectedListener")
        }
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        populateAppointments()
    }

    interface OnItemSelectedListener {
        fun onButtonSelected()
    }

    fun updateAppointmentList(appt: PatientDetailsModel) {
        appointmentArrayList.add(appt)
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private fun populateAppointments() {

        for (i in appointmentArrayList.indices) {
            PopulateTable(i)
        }
    }

    private fun SetToDateAndTime(appointment: PatientDetailsModel): String {
        val currentDateAndTime = System.currentTimeMillis() //Todays Date
        val formatDate = SimpleDateFormat("MMM d, yyyy") //Date Format

        val todaysDate = formatDate.format(currentDateAndTime) //Today's date formated
        val passDate = appointment.monthDate + " " + appointment.dayDate + ", " + appointment.yearDate //Tasks date formated the same way

        return if (todaysDate == passDate) { //Compare today's date and passed date, return time if dates match
            appointment.hourTime.toString() + ":" + appointment.minuteTime + " " + appointment.AMorPMTime
        } else appointment.monthDate + " " + appointment.dayDate + ", " + appointment.yearDate
//Otherwise, return the date

    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private fun PopulateTable(arrayListCounter: Int) {
        val appointmentTBL = activity!!.findViewById<View>(R.id.tblTaskContent) as TableLayout

        val newTableRow = TableRow(activity)
        val lp = TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT)
        newTableRow.layoutParams = lp

        val txtvName = TextView(activity)
        txtvName.layoutParams = lp
        txtvName.gravity = Gravity.CENTER
        txtvName.text = appointmentArrayList[arrayListCounter].name
        txtvName.width = 140
        txtvName.textSize = 12f
        txtvName.textAlignment = View.TEXT_ALIGNMENT_CENTER

        val txtvType = TextView(activity)
        txtvType.layoutParams = lp
        txtvType.gravity = Gravity.CENTER
        txtvType.text = appointmentArrayList[arrayListCounter].type
        txtvType.width = 93
        txtvType.textSize = 12f
        txtvType.textAlignment = View.TEXT_ALIGNMENT_CENTER

        val txtvDate = TextView(activity

        )
        txtvDate.layoutParams = lp
        txtvDate.gravity = Gravity.CENTER
        txtvDate.text = SetToDateAndTime(appointmentArrayList[arrayListCounter])
        txtvDate.width = 97
        txtvDate.textSize = 12f
        txtvDate.textAlignment = View.TEXT_ALIGNMENT_CENTER

        newTableRow.addView(txtvName)
        newTableRow.addView(txtvType)
        newTableRow.addView(txtvDate)
        appointmentTBL.addView(newTableRow, arrayListCounter + 1)

    }
}