package com.example.covid19_patient_manager.Controller

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import com.example.covid19_patient_manager.Model.PatientDetailsModel
import com.example.covid19_patient_manager.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

import java.util.Calendar;

class AddAppointmentFragment : Fragment() {

    internal lateinit var txtDate: TextView
    internal lateinit var txtTime: TextView

    private var year: Int = 0
    private var month: Int = 0
    private var day: Int = 0

    private var hour: Int = 0
    private var minute: Int = 0

    private var listener: AddAppointmentFragment.OnItemSelectedListener? = null

    private var mAuth: FirebaseAuth? = null

    private lateinit var database: DatabaseReference


    internal var ondate: DatePickerDialog.OnDateSetListener = DatePickerDialog.OnDateSetListener { view, selectedYear, selectedMonth, selectedDay ->
        year = selectedYear
        month = selectedMonth
        day = selectedDay

        txtDate.text = StringBuilder().append(month + 1)
                .append("-").append(day).append("-").append(year)
                .append(" ")
    }

    internal var onTime: TimePickerDialog.OnTimeSetListener = TimePickerDialog.OnTimeSetListener { view, selectedHour, selectedMinute ->
        hour = selectedHour
        minute = selectedMinute

        // set current time into textview
        txtTime.text = StringBuilder().append(pad(hour))
                .append(":").append(pad(minute))
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_add_appointment, container, false)

        val cancel = view.findViewById<View>(R.id.btnCancel) as Button
        cancel.setOnClickListener { listener!!.onCancel() }

        val addTask = view.findViewById<View>(R.id.btnAddTask) as Button
        addTask.setOnClickListener {
            val editAppointmentName = activity!!.findViewById<View>(R.id.editTaskName) as EditText
            val spinnerAppointmentType = activity!!.findViewById<View>(R.id.spnTaskType) as Spinner
            if (!editAppointmentName.text.toString().isEmpty()) {

                val app = PatientDetailsModel(editAppointmentName.text.toString(), spinnerAppointmentType.selectedItem.toString(),
                        DisplayTheMonthInCharacters(month), day, year, FormatTheHour(hour), minute, AMorPM(hour))

                        //add model to the firebase database
                        addModelToDatabase(app)


                listener!!.onAddAppointmentSelected(app)
            } else {
                    Toast.makeText(activity, "Please enter the patient Name", Toast.LENGTH_SHORT).show()
            }
        }

        return view
    }

     override fun onAttach(context: Context) {
        super.onAttach(context!!)
        if (context is AddAppointmentFragment.OnItemSelectedListener) {
            listener = context
        } else {
            throw ClassCastException(context!!.toString() + " must implemenet MyListFragment.OnItemSelectedListener")
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val bundle = arguments
        if (bundle != null) {
        }

        setCurrentDateAndTimeOnView()

        val time = activity!!.findViewById<View>(R.id.txtvTime) as TextView
        time.setOnClickListener { showTimePicker() }

        val date = activity!!.findViewById<View>(R.id.txttvDate) as TextView
        date.setOnClickListener { showDatePicker() }
    }

    interface OnItemSelectedListener {
        fun onAddAppointmentSelected(appt: PatientDetailsModel)
        fun onCancel()
    }


    // display current date and time
    fun setCurrentDateAndTimeOnView() {
        txtDate = activity!!.findViewById<View>(R.id.txttvDate) as TextView
        txtTime = activity!!.findViewById<View>(R.id.txtvTime) as TextView

        val c = Calendar.getInstance()
        year = c.get(Calendar.YEAR)
        month = c.get(Calendar.MONTH)
        day = c.get(Calendar.DAY_OF_MONTH)

        hour = c.get(Calendar.HOUR_OF_DAY)
        minute = c.get(Calendar.MINUTE)

        // set current time into textview
        txtTime.text = StringBuilder().append(pad(hour))
                .append(":").append(pad(minute))

        // set current date into textview
        txtDate.text = StringBuilder()
                // Month is 0 based, just add 1
                .append(month + 1).append("-").append(day).append("-")
                .append(year).append(" ")
    }

    private fun showDatePicker() {
        val date = DatePickerFragment()

        val args = Bundle()
        args.putInt("year", year)
        args.putInt("month", month)
        args.putInt("day", day)
        date.setArguments(args)
        date.arguments = args

        date.setCallBack(ondate)
        date.show(fragmentManager!!, "Date Picker")
    }

    private fun showTimePicker() {
        val time = TimePickerFragment()

        val args = Bundle()
        args.putInt("hour", hour)
        args.putInt("minute", month)
        time.arguments = args

        time.setCallBack(onTime)
        time.show(fragmentManager!!, "Time Picker")
    }

    /* Helper Methods */

    //Returns the Month Abbreviation for the corresponding number.
    private fun DisplayTheMonthInCharacters(passedMonth: Int): String {
        when (passedMonth) {
            0 -> return "Jan"
            1 -> return "Feb"
            2 -> return "Mar"
            3 -> return "Apr"
            4 -> return "May"
            5 -> return "Jun"
            6 -> return "Jul"
            7 -> return "Aug"
            8 -> return "Sept"
            9 -> return "Oct"
            10 -> return "Nov"
            11 -> return "Dec"
        }
        return ""
    }

    //Converts the 24 hours PassedHour to a 12 Hour time.
    private fun FormatTheHour(passedHour: Int): Int {
        var passedHour = passedHour
        if (passedHour > 12) {
            passedHour -= 12
        }
        return passedHour
    }

    //Returns AM or PM depending on the hour (1-24)
    private fun AMorPM(passedHour: Int): String {
        return if (passedHour > 12) {
            "PM"
        } else {
            "AM"
        }
    }


    private fun pad(c: Int): String {
        return if (c >= 10)
            c.toString()
        else
            "0$c"
    }

    class DatePickerFragment : DialogFragment() {
        internal lateinit  var ondateSet: DatePickerDialog.OnDateSetListener
        private var year: Int = 0
        private var month: Int = 0
        private var day: Int = 0

        fun setCallBack(ondate: DatePickerDialog.OnDateSetListener) {
            ondateSet = ondate
        }

        override fun setArguments(args: Bundle?) {
            super.setArguments(args)
            year = args!!.getInt("year")
            month = args.getInt("month")
            day = args.getInt("day")
        }

        override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
            return DatePickerDialog(activity!!, ondateSet, year, month, day)
        }
    }

    class TimePickerFragment : DialogFragment() {
        internal lateinit var onTimeSet: TimePickerDialog.OnTimeSetListener
        private var hour: Int = 0
        private var minute: Int = 0

        fun setCallBack(ontime: TimePickerDialog.OnTimeSetListener) {
            onTimeSet = ontime
        }

        override fun setArguments(args: Bundle?) {
            super.setArguments(args)
            hour = args!!.getInt("hour")
            minute = args.getInt("minute")
        }

        override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
            return TimePickerDialog(activity, onTimeSet, hour, minute, false)
        }
    }

    private fun addModelToDatabase( myModel: PatientDetailsModel)
    {
        database = Firebase.database.reference
        mAuth = FirebaseAuth.getInstance()

        database.child("Users").child(mAuth!!.uid.toString()).setValue(myModel)

    }



}


