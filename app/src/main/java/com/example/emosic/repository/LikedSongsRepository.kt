package com.example.emosic.repository

import com.example.emosic.data.FirestoreResponse
import kotlinx.coroutines.flow.Flow

typealias SongIds = List<String>
typealias SongIdsResponse = FirestoreResponse<SongIds>
typealias AddSongResponse = FirestoreResponse<Boolean>
typealias DeleteSongResponse = FirestoreResponse<Boolean>

interface LikedSongsRepository {
    fun getLikedSongsList(uid : String): Flow<SongIdsResponse>
    suspend fun addSongToLikedsongsList(uid : String, sid : String) : AddSongResponse
    suspend fun deleteSongFromLikedSongsList(uid : String, sid : String) : DeleteSongResponse
}