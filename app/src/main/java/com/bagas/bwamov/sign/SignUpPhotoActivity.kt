package com.bagas.bwamov.sign

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bagas.bwamov.home.HomeActivity
import com.bagas.bwamov.R
import com.bagas.bwamov.utils.Preferences
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import java.util.UUID
import kotlin.math.log

class SignUpPhotoActivity : AppCompatActivity(), PermissionListener {

//    inisalisasi image var
    val REQUEST_IMAGE_CAPTURE = 1
    var statusAdd:Boolean = false
    lateinit var filePath:Uri

//    inisalisasi storage var
    lateinit var storage : FirebaseStorage
    lateinit var storageReferensi : StorageReference
    lateinit var preferences: Preferences

//  inisalisasi database
    lateinit var user : User
    private lateinit var mFirebaseDatabase : DatabaseReference
    private lateinit var mFirebaseInstance : FirebaseDatabase


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_photo)

        preferences = Preferences(this)
        storage = FirebaseStorage.getInstance("gs://bwa-mov-731a2.appspot.com")
        storageReferensi = storage.getReference()

        mFirebaseInstance = FirebaseDatabase.getInstance("https://bwa-mov-731a2-default-rtdb.firebaseio.com/")
        mFirebaseDatabase = mFirebaseInstance.getReference("User")

        user = intent.getParcelableExtra<User>("data")!!

        Log.d("error user", "Pesan Error" + user)

//        mengambil nama dari page daftar
        findViewById<TextView>(R.id.iv_hello).text = "Selamat Datang \n"+user.nama

//        mengambil gambar  dari local hp
        findViewById<ImageView>(R.id.iv_add).setOnClickListener {
            if (statusAdd) {
                statusAdd = false
                findViewById<Button>(R.id.btn_save).visibility = View.INVISIBLE
                findViewById<ImageView>(R.id.iv_add).setImageResource(R.drawable.ic_baseline_add_circle_24)
                findViewById<ImageView>(R.id.iv_profile).setImageResource(R.drawable.user_pic)
            } else {
// memasukan gambar
//                Dexter.withActivity(this)
//                    .withPermission(android.Manifest.permission.CAMERA)
//                    .withListener(this)
//                    .check()
                ImagePicker.with(this)
                    .galleryOnly()
                    .start()
            }
        }

//        btn untuk lewati upload photo
        findViewById<Button>(R.id.btn_home).setOnClickListener {
            finishAffinity()
            val goHome = Intent(this@SignUpPhotoActivity, HomeActivity::class.java)
            startActivity(goHome)
        }


//        btn untuk save photo

        findViewById<Button>(R.id.btn_save).setOnClickListener {
//            validasi check file pathnya apa kah sudah ada atau belum
            if (filePath != null) {
//                munculkan progres bar
                val progresDialog = ProgressDialog(this)
                progresDialog.setTitle("Sedang Upload...")
                progresDialog.show()

//                masukan ke firebase storage
                val ref = storageReferensi.child("images/"+UUID.randomUUID().toString())
                ref.putFile(filePath)
                    .addOnSuccessListener {
//                        progres bar nya dimatikan
                        progresDialog.dismiss()
                        Toast.makeText(this@SignUpPhotoActivity, "Berhasil Upload Photo", Toast.LENGTH_LONG).show()

//                        simpan ke preferences
                        ref.downloadUrl.addOnSuccessListener {
//                            preferences.setvalues("url", it.toString())
                            saveToFirebase(it.toString())
                        }

//                        matikan semuanya dan push ke page home
                        finishAffinity()
                        var goHome = Intent(this@SignUpPhotoActivity, HomeActivity::class.java)
                        startActivity(goHome)
                    }
//                jika upload gagal
                    .addOnFailureListener {
                        progresDialog.dismiss()
                        Toast.makeText(this@SignUpPhotoActivity, "Upload Photo Gagal", Toast.LENGTH_LONG).show()
                    }
//                memunculkan berapa persen yang sudah di upload
                    .addOnProgressListener {
                        taskSnapShot -> val progress = 100.0 * taskSnapShot.bytesTransferred / taskSnapShot.totalByteCount
//                        munculkan di progres bar sudah brp persen
                        progresDialog.setMessage("Upload "+progress.toInt()+ " %")
                    }
            }
        }
    }
//    masukab gambar ke realtime database
    private fun saveToFirebase(url: String) {

        mFirebaseDatabase.child(user.username!!).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                user.url = url
                mFirebaseDatabase.child(user.username!!).setValue(user)

                preferences.setvalues("nama", user.nama.toString())
                preferences.setvalues("user", user.username.toString())
                preferences.setvalues("saldo", "" )
                preferences.setvalues("url", "")
                preferences.setvalues("email", user.email.toString())
                preferences.setvalues("status", "1")
                preferences.setvalues("url", url)

                finishAffinity()
                val intent = Intent(this@SignUpPhotoActivity, HomeActivity::class.java)
                startActivity(intent)


            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@SignUpPhotoActivity, ""+error.message, Toast.LENGTH_LONG).show()
            }

        })

    }

    override fun onPermissionGranted(response: PermissionGrantedResponse?) {
//        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also {
//            takePicture ->
//            takePicture.resolveActivity(packageManager)?.also {
//                startActivityForResult(takePicture, REQUEST_IMAGE_CAPTURE)
//            }
//        }

        ImagePicker.with(this)
            .galleryOnly()
            .start()


    }

    override fun onPermissionDenied(response: PermissionDeniedResponse?) {
        Toast.makeText(this@SignUpPhotoActivity, "Anda tidak bisa menambahkan poto profile", Toast.LENGTH_LONG).show()
    }

    override fun onPermissionRationaleShouldBeShown(
        permission: PermissionRequest?,
        token: PermissionToken?
    ) {

    }

    override fun onBackPressed() {
        Toast.makeText(this@SignUpPhotoActivity, "Tergesah? Klik tombol upload nanti saja", Toast.LENGTH_LONG).show()
    }

//    @SuppressLint("MissingSuperCall")
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        if (requestCode == REQUEST_IMAGE_CAPTURE && requestCode == RESULT_OK ) {
//            val bitmap = data?.extras?.get("data") as Bitmap
//            statusAdd = true
//            filePath = data.getData()!!
//            Glide.with(this)
//                .load(bitmap)
//                .apply(RequestOptions.circleCropTransform())
//                .into(findViewById<ImageView>(R.id.iv_profile))
//
//            findViewById<Button>(R.id.btn_save).visibility = View.VISIBLE
//            findViewById<ImageView>(R.id.iv_add).setImageResource(R.drawable.ic_btn_delete)
//        }
//    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            statusAdd = true
            filePath = data?.data!!
            Glide.with(this)
                .load(filePath)
                .apply(RequestOptions.circleCropTransform())
                .into(findViewById(R.id.iv_profile))

            Log.v("Gambar Berhasi", "file url upload"+filePath)
            findViewById<Button>(R.id.btn_save).visibility = View.VISIBLE
            findViewById<ImageView>(R.id.iv_add).setImageResource(R.drawable.ic_btn_delete)
        } else if (resultCode == ImagePicker.RESULT_ERROR)  {
            Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Cancel",Toast.LENGTH_SHORT ).show()
        }
    }

}