package com.example.covid19_patient_manager.Controller

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.covid19_patient_manager.Model.PatientDetailsModel
import com.example.covid19_patient_manager.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.dashboard_layout.*

class DashboardActivity : AppCompatActivity() {

//    var patientList : List<PatientDetailsModel> = List<PatientDetailsModel>(10,0)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dashboard_layout)


    }

    fun onClickLogout(view: View) {
        FirebaseAuth.getInstance().signOut()
        startActivity(Intent(this, MainActivity::class.java))

    }
}
