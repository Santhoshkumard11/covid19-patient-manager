package com.example.covid19_patient_manager.Controller

import android.app.Activity
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
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private var mAuth: FirebaseAuth? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_layout)

        mAuth = FirebaseAuth.getInstance()


    }

    override fun onStart() {
        super.onStart()

        val auth = FirebaseAuth.getInstance()

        if ( auth.currentUser != null)
        {
            Toast.makeText(applicationContext, "Welcome Again ${auth.currentUser!!.displayName}", Toast.LENGTH_LONG).show()
            // already signed in
            startActivity(Intent( this, DashboardActivity::class.java))
            finish()
        }


    }
    override fun onResume() {
        super.onResume()
        progressBar.visibility = View.GONE
        Toast.makeText(this, "Successfully Logged out",Toast.LENGTH_LONG).show()
    }


    // login with email and password
    fun onClickLogin(view: View) {
        if (TextUtils.isEmpty(emailfield.text.toString())) {
            Toast.makeText(applicationContext, "Enter email address!", Toast.LENGTH_SHORT).show()
            return
        }

        if (TextUtils.isEmpty(passwordfield.text.toString())) {
            Toast.makeText(applicationContext, "Enter password!", Toast.LENGTH_SHORT).show()
            return
        }

        progressBar.visibility = View.VISIBLE

        //authenticate user
        mAuth!!.signInWithEmailAndPassword(emailfield.text.toString(), passwordfield.text.toString())
            .addOnCompleteListener(this) { task ->
                // If sign in fails, display a message to the user. If sign in succeeds
                // the auth state listener will be notified and logic to handle the
                // signed in user can be handled in the listener.
                progressBar.visibility = View.GONE
                if (!task.isSuccessful) {
                    // there was an error
                    if (passwordfield.text.toString().length < 6) {
                        passwordfield.error = getString(R.string.minimum_password)
                    } else {
                        Toast.makeText(this, getString(R.string.auth_failed), Toast.LENGTH_LONG).show()
                    }
                } else {

                    startActivity(Intent(this, DashboardActivity::class.java))
                    finish()
                }
            }
    }






    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if( requestCode == 1234)
        {
            val response = IdpResponse.fromResultIntent(data)

            if ( resultCode == Activity.RESULT_OK)
            {
                //successfully signed in
                val user = FirebaseAuth.getInstance().currentUser
                Toast.makeText(applicationContext, "Successfully signed in", Toast.LENGTH_LONG).show()

                val dashboard = Intent(this, DashboardActivity::class.java)
//
                startActivity(dashboard)

            }
            else
                Toast.makeText(applicationContext, "Failed to sign in", Toast.LENGTH_LONG).show()




        }

        if( requestCode == 1111)
        {
            val response = IdpResponse.fromResultIntent(data)

            if ( resultCode == Activity.RESULT_OK)
            {
                //successfully signed in
                val user = FirebaseAuth.getInstance().currentUser
                Toast.makeText(applicationContext, "Successfully signed in", Toast.LENGTH_LONG).show()

                val dashboard = Intent(this, DashboardActivity::class.java)

                startActivity(dashboard)
            }
            else
                Toast.makeText(applicationContext, "Failed to sign in", Toast.LENGTH_LONG).show()

        }
    }

    //login with google
    fun onClickRegister (view: View)
    {
        val providers = arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build())


        startActivityForResult(
            AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .build(), 1111)
    }


    //login with email and password
    fun onClickGoogleLogin ( view: View){

        val providers = arrayListOf(
            AuthUI.IdpConfig.GoogleBuilder().build())


        startActivityForResult(
            AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .build(), 1234)

    }


}

