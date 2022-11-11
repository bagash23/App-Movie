package com.bagas.bwamov.sign

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class User (
    var email:String ?= "",
    var nama:String ?= "",
    var password:String ?= "",
    var saldo:String ?= "",
    var url:String ?= "",
    var username:String ?= ""
) : Parcelable