package com.example.covid19_patient_manager.Controller

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.ContextThemeWrapper
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.covid19_patient_manager.Model.User
import com.example.covid19_patient_manager.R
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.dashboard_layout.*

class MainDashboard : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
            setContentView(R.layout.dashboard_layout)

    }


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


    override fun onBackPressed() {
        super.onBackPressed()

    }

}