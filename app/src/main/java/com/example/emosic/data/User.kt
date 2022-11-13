package com.example.emosic.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
@Parcelize
data class User(
    var name: String = "",
    var email : String = "",
    var age : Int = 0,
    var likedSongs : ArrayList<String> = ArrayList(),
    var subscribedChannels : ArrayList<String> = ArrayList()
) : Parcelable
