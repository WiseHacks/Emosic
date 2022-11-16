package com.example.emosic.data


import android.os.Parcelable
import kotlinx.parcelize.Parcelize
@Parcelize
/*TODO - Firestore collections*/
data class Song(
    var title: String = "",
) : Parcelable