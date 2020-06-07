package com.example.covid19_patient_manager.Controller

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.covid19_patient_manager.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase

class MainDashboard : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
            setContentView(R.layout.dashboard_layout)

        fun onClickAddPatient(view: View){

            startActivity(Intent(this,AddEmployeeActivity::class.java))
        }

        fun onClickPatientList(view: View){

            startActivity(Intent(this,DashboardActivity::class.java))
        }

        fun onClickSignOut(view: View){
            FirebaseAuth.getInstance().signOut()

            startActivity(Intent(this,LoginActivity::class.java))
        }

    }

}