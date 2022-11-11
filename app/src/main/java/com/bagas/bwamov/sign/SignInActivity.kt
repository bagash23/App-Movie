package com.bagas.bwamov.sign

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.bagas.bwamov.home.HomeActivity
import com.bagas.bwamov.R
import com.bagas.bwamov.utils.Preferences
import com.google.firebase.database.*

class SignInActivity : AppCompatActivity() {

    lateinit var iEmail:String
    lateinit var iPassword:String

//    menambhakan inisalisasi databases
    lateinit var  mDatabse : DatabaseReference
    lateinit var preference : Preferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)


//        setting db
        mDatabse = FirebaseDatabase.getInstance("https://bwa-mov-731a2-default-rtdb.firebaseio.com/").getReference("User")
        preference = Preferences(this)

//        agar tidak mengulang onBoarding hanya saat membuka pertama kali
        preference.setvalues("onboarding", "1")
        if (preference.getValues("status").equals("1")) {
            finishAffinity()
            val goHome = Intent(this@SignInActivity, HomeActivity::class.java)
            startActivity(goHome)
        }

        findViewById<Button>(R.id.btn_home).setOnClickListener {
            iEmail = findViewById<EditText>(R.id.et_username).text.toString()
            iPassword = findViewById<EditText>(R.id.et_paswword).text.toString()

//            membuat validasi jika tidak ada isinya
            if (iEmail.equals("")) {
                findViewById<EditText>(R.id.et_username).error = "Silahkan tuliskan username Anda"
                findViewById<EditText>(R.id.et_username).requestFocus()
            } else if (iPassword.equals("")) {
                findViewById<EditText>(R.id.et_paswword).error = "Silahkan tuliskan kata sandi Anda"
                findViewById<EditText>(R.id.et_paswword).requestFocus()
            } else {
                pushLogin(iEmail, iPassword)
            }
        }

//        masuk kehalaman daftar akun
        findViewById<Button>(R.id.btn_daftar).setOnClickListener {
            val intent = Intent(this@SignInActivity, SignUpActivity::class.java)
            startActivity(intent)
        }


    }

//    fungsi menjalankan login
    private fun pushLogin(iEmail: String, iPassword: String) {
//    pengecekan email
        mDatabse.child(iEmail).addValueEventListener(object  : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
//                membuat class untuk menampung detail user
                val user = dataSnapshot.getValue(User::class.java)
//                membuat case
                if (user == null)  {
                    Toast.makeText(this@SignInActivity, "User tidak ditemukan", Toast.LENGTH_LONG).show()
                } else {
//                    match password
                    if (user.password.equals(iPassword)) {

//                        simpan data
                        preference.setvalues("nama", user.nama.toString())
                        preference.setvalues("user", user.username.toString())
                        preference.setvalues("url", user.url.toString())
                        preference.setvalues("email", user.email.toString())
                        preference.setvalues("saldo", user.saldo.toString())
                        preference.setvalues("status", "1")

                        val intent = Intent(this@SignInActivity, HomeActivity::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this@SignInActivity, "Kata sandi tidak sama", Toast.LENGTH_LONG).show()
                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(this@SignInActivity, databaseError.message, Toast.LENGTH_LONG).show()
            }

        })
    }
}