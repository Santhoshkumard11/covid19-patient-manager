package com.example.covid19_patient_manager.Controller

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.view.Gravity
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
import com.example.covid19_patient_manager.Model.User

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {

    private var mAuth: FirebaseAuth? = null

    private lateinit var database: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_layout)

        database = Firebase.database.reference
        mAuth = FirebaseAuth.getInstance()


    }

    override fun onStart() {
        super.onStart()

        //getting the firebase authenticaiton and database instance
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
        val user = FirebaseAuth.getInstance().currentUser
//        if( user == null)
//            Toast.makeText(this, "Successfully Logged out",Toast.LENGTH_LONG).show()
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
                        Toast.makeText(this,getString(R.string.auth_failed), Toast.LENGTH_LONG).show()
                    }
                } else {

                    loginTransationLogsToDatabase()


                    startActivity(Intent(this, DashboardActivity::class.java))
                    finish()
                }
            }
    }






    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        //login in with google sign in
        if( requestCode == 1234)
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

        //login with gmail
        if( requestCode == 1111)
        {
            val response = IdpResponse.fromResultIntent(data)

            if ( resultCode == Activity.RESULT_OK)
            {
                //successfully signed in

                Toast.makeText(applicationContext, "Successfully signed in", Toast.LENGTH_LONG).show()
                startActivity(Intent(this, DashboardActivity::class.java))
            }
            else
                Toast.makeText(applicationContext, "Failed to sign in", Toast.LENGTH_LONG).show()

        }
    }

    //register a new user
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


    //login with google
    fun onClickGoogleLogin ( view: View){

        val providers = arrayListOf(
            AuthUI.IdpConfig.GoogleBuilder().build())


        startActivityForResult(
            AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .build(), 1234)

    }


    fun loginTransationLogsToDatabase() {

        val user = FirebaseAuth.getInstance().currentUser
//        //                    writing to the database


        // Write a message to the database


        val userId = user!!.uid
        val userName = user!!.displayName
        val emailAddress = user!!.email

        val myUser = User(userId.toString(), userName.toString(), emailAddress.toString())

        database.child(userId.toString()).setValue(myUser)

        Toast.makeText(this, "data added to the database",Toast.LENGTH_LONG).show()


    }


}

