package com.example.emosic.data


import android.os.Parcelable
import kotlinx.parcelize.Parcelize
@Parcelize
/*TODO - Firestore collections*/
data class Video(
    var title: String = "",
) : Parcelable