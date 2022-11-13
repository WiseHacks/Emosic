package com.example.emosic.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
@Parcelize
data class Channel(
    var title: String = "",
) : Parcelable