package com.example.emosic.utils

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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.emosic.R
import com.example.emosic.data.Song
import com.example.emosic.ui.theme.PlayColor
import com.example.emosic.ui.theme.ProfileTextColor

@Composable
fun SongCard(
    song: Song?,
    removeSong:(songId : String) -> Unit
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
                                removeSong(song.id)
                            }
                        },
                        painter = painterResource(id = R.drawable.ic_dislikeicon),
                        contentDescription = "play"
                    )
                    Spacer(modifier = Modifier.height(40.dp))
                    Row(Modifier.rotate(270f), verticalAlignment = Alignment.CenterVertically) {
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
                    SongCardHelper(key = "Duration", value = song.duration_ms)
                    SongCardHelper(key = "Genre", value = song.genres)
                    SongCardHelper(key = "Album", value = song.album_name)
                }
            }
        }
    }
}
