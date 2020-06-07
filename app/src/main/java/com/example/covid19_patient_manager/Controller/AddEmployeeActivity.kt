package com.example.covid19_patient_manager.Controller

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.covid19_patient_manager.Model.PatientDetailsModel
import com.example.covid19_patient_manager.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_add_employee.*

class AddEmployeeActivity : AppCompatActivity() {

    private var myHelper: DatabaseHelper? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_employee)

        myHelper = DatabaseHelper()
        myHelper!!.open()
    }

    fun addButtonPressed(view: View) {
        var patient : PatientDetailsModel = PatientDetailsModel()

        patient.name = patientNameEditText.text.toString()
        patient.gender = genderSelectSpinner.selectedItem.toString()
//        patient.doctorName = FirebaseAuth.getInstance().currentUser!!.uid.toString()

        myHelper!!.add(patient)

        finish()
    }

    fun onClickCancel(view: View){

        startActivity(Intent(this,DashboardActivity::class.java))
    }



}
