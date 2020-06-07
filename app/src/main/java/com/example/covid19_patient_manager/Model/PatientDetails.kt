package com.example.covid19_patient_manager.Model

import android.content.Context
import android.widget.ArrayAdapter
import java.text.DateFormat
import java.util.*
import kotlin.collections.ArrayList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
data class PatientDetailsModel
    (var name: String = "", var gender: String = "",
     var monthDate: String = "", var dayDate: Int = 1, var yearDate: Int = 1,
     var hourTime: Int = 1, var minuteTime: Int = 1, var AMorPMTime: String = "",var counter :String = "" ,
     var doctorName: String = "", var _id: String = "")


open class User ( var userId:String = "",var usename: String = "", var emailId:String = "")
