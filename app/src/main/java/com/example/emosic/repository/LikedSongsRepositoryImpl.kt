package com.example.emosic.repository

import com.example.emosic.data.FirestoreResponse
import com.google.android.youtube.player.internal.e
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LikedSongsRepositoryImpl @Inject constructor() : LikedSongsRepository {
    override fun getLikedSongsList(uid: String) = callbackFlow {
        val db = FirebaseFirestore.getInstance()
        val snapshotListener =
            db.collection("User").document(uid).addSnapshotListener { snapshot, e ->
                val songIdsResponse = if (snapshot != null) {
                    val SongIds = snapshot.get("likedSongs") as List<String>
                    FirestoreResponse.Success(SongIds)
                } else {
                    FirestoreResponse.Failure(e)
                }
                trySend(songIdsResponse)
            }
        awaitClose {
            snapshotListener.remove()
        }
    }
    override suspend fun addSongToLikedsongsList(uid: String, sid: String): AddSongResponse {
        val db = FirebaseFirestore.getInstance()
        return try {
            db.collection("User").document(uid).update("likedSongs", FieldValue.arrayUnion(sid))
                .await()
            FirestoreResponse.Success(true)
        } catch (e: Exception) {
            FirestoreResponse.Failure(e)
        }
    }
    override suspend fun deleteSongFromLikedSongsList(uid: String, sid: String): DeleteSongResponse {
        val db = FirebaseFirestore.getInstance()
        return try {
            db.collection("User").document(uid).update("likedSongs", FieldValue.arrayRemove(sid))
                .await()
            FirestoreResponse.Success(true)
        } catch (e: Exception) {
            FirestoreResponse.Failure(e)
        }
    }
}