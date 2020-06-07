package com.example.covid19_patient_manager.Controller

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextThemeWrapper
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import com.example.covid19_patient_manager.Model.PatientDetailsModel
import com.example.covid19_patient_manager.PatientAdapter
import com.example.covid19_patient_manager.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_emp_list.*

class DashboardActivity : AppCompatActivity() {

    private var adapter : PatientAdapter? = null

    private lateinit var myHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_emp_list)

        val arrayOfUsers = ArrayList<PatientDetailsModel>()

        myHelper = DatabaseHelper()
        myHelper.open()

        list_view!!.emptyView = findViewById(R.id.empty)

        adapter = PatientAdapter(this, arrayOfUsers)

        list_view!!.adapter = adapter

        myHelper.allEmployees(adapter)

        list_view!!.onItemClickListener =
            AdapterView.OnItemClickListener { adapterView, view, position, id ->
                var user = adapter?.getItem(position)

                val modify_intent = Intent(applicationContext, ModifyEmployeeActivity::class.java)
                modify_intent.putExtra("name", user?.name)
                modify_intent.putExtra("address", user?.gender)
                modify_intent.putExtra("id", user?._id)

                startActivity(modify_intent)
            }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        val id = item.itemId
        if (id == R.id.add_record) {
            val add_mem = Intent(this, AddEmployeeActivity::class.java)
            startActivity(add_mem)
        }
        return super.onOptionsItemSelected(item)
    }

    fun onClickAddPatient(view: View){

        startActivity(Intent(this,AddEmployeeActivity::class.java))
    }

    fun onClickSwitchUser(view: View){


        AlertDialog.Builder(ContextThemeWrapper(this, R.style.AlertDialogCustom))
            .setIcon(android.R.drawable.ic_dialog_alert)
            .setTitle("Switch user for sure???")
            .setMessage("")
            .setPositiveButton("Yes",
                DialogInterface.OnClickListener { dialog, which -> finish() })
            .setNegativeButton("No, Stay", null)
            .show()
        FirebaseAuth.getInstance().signOut()
        startActivity(Intent(this,LoginActivity::class.java))
    }

    fun onClickLogout(view : View) {
            FirebaseAuth.getInstance().signOut()
            AlertDialog.Builder(ContextThemeWrapper(this, R.style.AlertDialogCustom))
            .setIcon(android.R.drawable.ic_dialog_alert)
            .setTitle("Do you really want to quit???")
            .setMessage("")
            .setPositiveButton("Yes, Quit!!",
                DialogInterface.OnClickListener { dialog, which -> finish() })
            .setNegativeButton("No, Stay", null)
            .show()
            finish()
    }

    fun onClickCancel(view: View) {}


}