package com.example.covid19_patient_manager.Controller

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.covid19_patient_manager.Model.PatientDetailsModel
import com.example.covid19_patient_manager.R
import kotlinx.android.synthetic.main.activity_modify_employee.*

class ModifyEmployeeActivity : AppCompatActivity() {


    var ID : Int = 0

    private var myHelper: DatabaseHelper? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_modify_employee)

        myHelper = DatabaseHelper()
        myHelper!!.open()

        val intent = intent
        val ID = intent.getStringExtra("id")
        val name = intent.getStringExtra("name")
        val desc = intent.getStringExtra("address")

        name_edittext!!.setText(name)
        address_edittext!!.setText(desc)
    }

    private fun returnToMainActivity() {
        finish()
    }

    fun updateButtonPressed(view: View) {
        var patient: PatientDetailsModel = PatientDetailsModel()

        patient.name = name_edittext!!.text.toString()
        patient.gender = address_edittext!!.text.toString()

        myHelper!!.update(patient)

        returnToMainActivity()
    }

    fun deleteButtonPressed(view: View) {
        myHelper!!.delete(ID)
        returnToMainActivity()
    }

    fun onClickCancel(view: View){
        returnToMainActivity()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        returnToMainActivity()
    }

}
