package com.example.emosic.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.emosic.data.FirestoreResponse
import com.example.emosic.data.Song
import com.example.emosic.repository.*
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LikedSongsViewModel @Inject constructor(private val likedSongsRepository : LikedSongsRepository) : ViewModel() {
    var songIdsResponse: SongIdsResponse by mutableStateOf(FirestoreResponse.Loading)
        private set
    var addSongResponse: AddSongResponse by mutableStateOf(FirestoreResponse.Success(false))
        private set
    var deleteSongResponse: DeleteSongResponse by mutableStateOf(FirestoreResponse.Success(false))
        private set
    init {
        getLikedSongs(uid = FirebaseAuth.getInstance().currentUser?.uid.toString())
    }
    private fun getLikedSongs(uid : String) = viewModelScope.launch{
        likedSongsRepository.getLikedSongsList(uid).collect{response ->
            songIdsResponse = response
        }
    }
    fun addSong(uid : String, sid : String) = viewModelScope.launch {
        addSongResponse = FirestoreResponse.Loading
        addSongResponse = likedSongsRepository.addSongToLikedsongsList(uid, sid)
    }
    fun deleteSong(uid : String, sid : String) = viewModelScope.launch {
        deleteSongResponse = FirestoreResponse.Loading
        deleteSongResponse = likedSongsRepository.deleteSongFromLikedSongsList(uid, sid)
    }
}