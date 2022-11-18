package com.example.emosic.data


import android.os.Parcelable
import kotlinx.parcelize.Parcelize
@Parcelize
/*TODO - Firestore collections*/
data class Song(
    var id : String = "",
    var artist_name : String = "",
    var artist_uri : String = "",
    var track_name : String = "",
    var track_uri : String = "",
    var track_href : String = "",
    var duration_ms : String = "",
    var album_name : String = "",
    var album_uri : String = "",
    var genres : String = "",
) : Parcelable
