package com.example.covid19_patient_manager.Controller

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import com.example.covid19_patient_manager.Model.AdminModel
import com.example.covid19_patient_manager.R
import kotlinx.android.synthetic.main.login_layout.*
import com.example.covid19_patient_manager.Controller.ValidationClass.*

class MainActivity : AppCompatActivity() {

    var admin: AdminModel = AdminModel("", "")

    //validation class for email and password validation
    val v : ValidationClass = ValidationClass()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_layout)

        admin.emailid = emailfield.text.toString()
        admin.password = passwordfield.text.toString()

    }


    fun onClickLogin(view: View) {

        if (v.isValidEmail(emailfield.text.toString()) && v.isValidPassword(passwordfield.text.toString())) {

            val dashboard = Intent(this, DashboardActivity::class.java)

            startActivity(dashboard)

        }
        else {


            if(!v.isValidEmail(emailfield.text.toString()))
                Toast.makeText(this, "Enter a valid email id", Toast.LENGTH_SHORT).show()

            if(!v.isValidPassword(passwordfield.text.toString()))
                Toast.makeText(this, "Enter a valid  password", Toast.LENGTH_LONG).show()


        }


    }


    fun onClickRegister(view: View) {
        val signUp = Intent(this, SignUpActivity::class.java)

        startActivity(signUp)
    }

}

