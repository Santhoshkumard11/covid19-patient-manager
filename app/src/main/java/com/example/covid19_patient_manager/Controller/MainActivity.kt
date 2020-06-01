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


class MainActivity : AppCompatActivity() {

    var admin: AdminModel = AdminModel("", "")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_layout)

        admin.emailid = emailfield.text.toString()
        admin.password = passwordfield.text.toString()

    }

    fun isValidEmail(target: CharSequence?): Boolean {
        return !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target)
            .matches()
    }

    fun isValidPassword(password: String?) : Boolean {
        password?.let {
            val passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{4,}$"
            val passwordMatcher = Regex(passwordPattern)

            return passwordMatcher.find(password) != null
        } ?: return false
    }

    fun onClickLogin(view: View) {

        if (isValidEmail(emailfield.text.toString()) && isValidPassword(passwordfield.text.toString())) {

            val dashboard = Intent(this, DashboardActivity::class.java)

            startActivity(dashboard)

        }
        else {


            if(!isValidEmail(emailfield.text.toString()))
                Toast.makeText(this, "Enter a valid email id", Toast.LENGTH_SHORT).show()

            if(!isValidPassword(passwordfield.text.toString()))
                Toast.makeText(this, "Enter a valid  password", Toast.LENGTH_LONG).show()


        }


    }


    fun onClickRegister(view: View) {
        val signUp = Intent(this, SignUpActivity::class.java)

        startActivity(signUp)
    }

}

