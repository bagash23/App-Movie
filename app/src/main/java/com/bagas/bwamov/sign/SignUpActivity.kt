package com.bagas.bwamov.sign

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.bagas.bwamov.R
import com.google.firebase.database.*

class SignUpActivity : AppCompatActivity() {

    lateinit var sUsername:String
    lateinit var sNamaLengkap:String
    lateinit var sEmail:String
    lateinit var sPassword:String

//    inisalisai dari firebase
    lateinit var mDatabseReference: DatabaseReference
    lateinit var mFirebaseInstance: FirebaseDatabase
    lateinit var mDatabase: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        mFirebaseInstance = FirebaseDatabase.getInstance("https://bwa-mov-731a2-default-rtdb.firebaseio.com/")
        mDatabase = FirebaseDatabase.getInstance().getReference()
        mDatabseReference = mFirebaseInstance.getReference("User")

        findViewById<Button>(R.id.btn_lanjutkan).setOnClickListener {
            sUsername = findViewById<EditText>(R.id.et_username_daftar).text.toString()
            sNamaLengkap = findViewById<EditText>(R.id.et_name).text.toString()
            sEmail = findViewById<EditText>(R.id.et_email_daftar).text.toString()
            sPassword = findViewById<EditText>(R.id.et_password_daftar).text.toString()

//            validasi jika tidak memasung data kosong
            if (sUsername.equals("")){
                findViewById<EditText>(R.id.et_username_daftar).error="Silahkan masukan username anda"
                findViewById<EditText>(R.id.et_username_daftar).requestFocus()
            } else if (sNamaLengkap.equals("")) {
                findViewById<EditText>(R.id.et_name).error = "Silahkan masukan nama lengkap anda"
                findViewById<EditText>(R.id.et_name).requestFocus()
            } else if (sEmail.equals("")) {
                findViewById<EditText>(R.id.et_email_daftar).error = "Silahkan masukan email anda"
                findViewById<EditText>(R.id.et_email_daftar).requestFocus()
            } else if (sPassword.equals("")) {
                findViewById<EditText>(R.id.et_password_daftar).error = "Silahkan masukan email anda"
                findViewById<EditText>(R.id.et_password_daftar).requestFocus()
            } else {
//                membuat fungsi untuk menjalnkan perintah data
                saveUsername(sUsername, sNamaLengkap, sEmail, sPassword)
            }
        }

    }

    private fun saveUsername(
        sUsername: String,
        sNamaLengkap: String,
        sEmail: String,
        sPassword: String
    ) {
//        menampung data agar fungsi nya tidak terlalu banyak
        val user = User()
        user.email = sEmail
        user.username = sUsername
        user.password = sPassword
        user.nama = sNamaLengkap

//        menjalankan fungsi
        if (sUsername != null) {
            checingUsername(sUsername, user)
        }

    }

    private fun checingUsername(sUsername: String, data: User) {
        mDatabseReference.child(sUsername).addValueEventListener(object  : ValueEventListener{
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val user = dataSnapshot.getValue(User::class.java)
                if (user == null) {
//                    simpan ke database
                    mDatabseReference.child(sUsername).setValue(data)
                    val goSignUpPhoto = Intent(this@SignUpActivity, SignUpPhotoActivity::class.java).putExtra("data", data)
                    startActivity(goSignUpPhoto)

                } else {
                    Toast.makeText(this@SignUpActivity, "user sudah digunakan", Toast.LENGTH_LONG).show()
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(this@SignUpActivity, ""+databaseError.message, Toast.LENGTH_LONG).show()
            }

        })
    }
}