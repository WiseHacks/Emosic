package com.example.emosic.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import com.example.emosic.R
import com.example.emosic.data.APIResponse
import com.example.emosic.data.Song
import com.example.emosic.ui.theme.PlayColor
import com.example.emosic.ui.theme.ProfileTextColor
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun SongCard_(
    context: Context,
    song: Song?,
    addSong: (songId: String) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(3.dp),
        shape = RoundedCornerShape(15.dp),
        elevation = 10.dp
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Box(modifier = Modifier.fillMaxWidth(0.15f)) {
                Column(
                    modifier = Modifier.padding(10.dp),
                    verticalArrangement = Arrangement.SpaceBetween,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
//                    Image(painter = painterResource(id = R.drawable.ic_playicon), contentDescription = "play")
//                    Spacer(modifier = Modifier.height(30.dp))
                    Image(
                        modifier = Modifier.clickable {
                            if (song != null) {
                                val db = FirebaseFirestore.getInstance()
                                val temp = HashMap<String, Any>()
                                temp["id"] = song.id
                                temp["track_name"] = song.track_name
                                temp["track_uri"] = song.track_uri
                                temp["artist_name"] = song.artist_name
                                temp["artist_uri"] = song.artist_uri
                                temp["album_name"] = song.album_name
                                temp["album_uri"] = song.album_uri
                                temp["duration_ms"] = song.duration_ms
                                temp["genres"] = song.genres
                                db.collection("Song").document(song.id).set(temp)
                                    .addOnCompleteListener {
                                        if (it.isSuccessful) {
                                            Toast.makeText(
                                                context,
                                                "Added to liked songs",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                            addSong(song.id)
                                        }
                                    }
                            }
                        },
                        painter = painterResource(id = R.drawable.ic_likeicon),
                        contentDescription = "play"
                    )
                    Spacer(modifier = Modifier.height(40.dp))
                    Row(
                        Modifier
                            .rotate(270f)
                            .clickable {
                                val uri = Uri.parse("https://open.spotify.com/track/${song?.id}")
                                val intent = Intent(Intent.ACTION_VIEW, uri)
                                context.startActivity(intent)
                            },
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            text = "PLAY",
                            fontFamily = FontFamily(Font(R.font.kanit_regular)),
                            fontWeight = FontWeight.Bold,
                            color = PlayColor
                        )
                        Spacer(modifier = Modifier.width(2.dp))
                        Image(
                            painter = painterResource(id = R.drawable.ic_playicon),
                            contentDescription = "play"
                        )
                    }
                }
            }
            Box(modifier = Modifier.fillMaxWidth(1f), contentAlignment = Alignment.Center) {
                Column(modifier = Modifier.padding(vertical = 5.dp)) {
                    SongCardHelper(key = "Track", value = song!!.track_name)
                    SongCardHelper(key = "Artist", value = song.artist_name)
                    SongCardHelper(
                        key = "Duration",
                        value = ((song.duration_ms.toInt() / 1000) / 60).toString() +
                                " m " + ((song.duration_ms.toInt() / 1000) % 60).toString() + " s"
                    )
                    SongCardHelper(key = "Genre", value = song.genres.toString())
                    SongCardHelper(key = "Album", value = song.album_name)
                }
            }
        }
    }
}
