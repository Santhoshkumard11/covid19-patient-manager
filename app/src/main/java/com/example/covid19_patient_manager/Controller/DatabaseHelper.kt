package com.example.covid19_patient_manager.Controller

import android.content.pm.PackageItemInfo
import android.util.Log
import com.example.covid19_patient_manager.Model.PatientDetailsModel
import com.example.covid19_patient_manager.PatientAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class DatabaseHelper {

    private lateinit var database: DatabaseReference

    val auth = FirebaseAuth.getInstance()

    fun allEmployees(adapter: PatientAdapter?) {
        var myReference: DatabaseReference = database.child("Users/${auth.uid.toString()}/Paitent_Detials")

        myReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                adapter?.clear()

                var counter : Int = 1;

                for (myUser in dataSnapshot.children) {
                    val user = myUser.getValue(PatientDetailsModel::class.java)

                    // Check for null
                    if (user == null) {
                        Log.e("DatabaseHelper", "User data is null!")
                        return
                    }

                    user._id = myUser.key!!
                    user.counter = Integer.toString(counter++)
                    adapter?.add(user)
                }
            }
            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.e("DatabaseHelper", "Failed to read user", error.toException())
            }
        })
    }

    fun open() {
        database = FirebaseDatabase.getInstance().reference
    }

    fun add(patient: PatientDetailsModel) {
        val key = database.child("Users/${auth.uid.toString()}").push().key

        database.child("Users/${auth.uid.toString()}/Paitent_Detials/${patient.counter}").setValue(patient)

    }

    fun update(patient: PatientDetailsModel) {
        database.child("Users/${auth.uid.toString()}/Paitent_Detials/${patient.counter}").setValue(patient)
    }

    fun delete(ID : String) {
        database.child("Users/${auth.uid.toString()}/Patient_Detials").child(ID).removeValue()
    }
}
