package com.example.covid19_patient_manager.Controller

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextThemeWrapper
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.covid19_patient_manager.Model.PatientDetailsModel
import com.example.covid19_patient_manager.R
import com.google.firebase.auth.FirebaseAuth

class DashboardActivity : AppCompatActivity(), MainFragment.OnItemSelectedListener, AddAppointmentFragment.OnItemSelectedListener {

    internal lateinit var myFragment: MainFragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val fm = supportFragmentManager
        myFragment = MainFragment()

        // add
        val ft = fm.beginTransaction()
        ft.add(R.id.myContainer, myFragment)
        ft.commit()
    }

    override fun onAddAppointmentSelected(appt: PatientDetailsModel) {
        val fragmentManager = supportFragmentManager

        myFragment.updateAppointmentList(appt)

        val ft = fragmentManager.beginTransaction()
        ft.replace(R.id.myContainer, myFragment)
        ft.commit()
    }

    override fun onCancel() {
        val fragmentManager = supportFragmentManager

        val ft = fragmentManager.beginTransaction()
        ft.replace(R.id.myContainer, myFragment)
        ft.commit()
    }


    override fun onButtonSelected() {
        val fragmentManager = supportFragmentManager
        val ft = fragmentManager.beginTransaction()
        ft.replace(R.id.myContainer, AddAppointmentFragment())
        ft.commit()
    }

    fun onClickLogout(view: View) {
        FirebaseAuth.getInstance().signOut()
        val builder = AlertDialog.Builder(ContextThemeWrapper(this, R.style.AlertDialogCustom))

        with(builder)
        {
            setTitle("Do you really want to quit???")
            setMessage("")
                .setCancelable(false)
                // positive button text and action
                .setPositiveButton("Yes, Quit", DialogInterface.OnClickListener {
                        dialog, id -> finish()
                })
                // negative button text and action
                .setNegativeButton("No, Stay!!", DialogInterface.OnClickListener {
                        dialog, id -> dialog.cancel()
                })

            show()
        }

//        startActivity(Intent(this, LoginActivity::class.java))

    }


    fun onClickSwithUser(view: View){

        FirebaseAuth.getInstance().signOut()

        startActivity(Intent(this, LoginActivity::class.java))

    }


}
