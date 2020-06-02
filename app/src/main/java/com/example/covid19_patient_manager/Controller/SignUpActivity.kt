package com.example.covid19_patient_manager.Controller

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.covid19_patient_manager.R
import kotlinx.android.synthetic.main.login_layout.*

class SignUpActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.signup_layout)

        var v : ValidationClass = ValidationClass()

        fun onClickSighup(view: View) {

//            println("inside the fun")

            if (v.isValidEmail(emailfield.text.toString()) && v.isValidPassword(passwordfield.text.toString())) {

//
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


    }
}
