package com.example.covid19_patient_manager.Model

import java.text.DateFormat
import java.util.*

open class PatientDetailsModel
    (var name: String = "", var type: String = "",
     var monthDate: String = "", var dayDate: Int = 1, var yearDate: Int = 1,
     var hourTime: Int = 1, var minuteTime: Int = 1, var AMorPMTime: String = "",var count :Int = 0 )


open class User ( var userId:String = "",var usename: String = "", var emailId:String = "")
