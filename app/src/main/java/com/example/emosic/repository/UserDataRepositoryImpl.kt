package com.example.emosic.repository

import android.util.Log
import com.example.emosic.data.User
import com.example.emosic.utils.Params
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class UserDataRepositoryImpl {
    suspend fun getUserData(): User? {
        return try {
            val db = FirebaseFirestore.getInstance()
            val snapshot = db.collection("User")
                .document(FirebaseAuth.getInstance().currentUser?.uid.toString())
                .get()
                .await()
            var user:User? = null
            if(snapshot.exists()){
                user = User(
                    snapshot.get("name").toString(),
                    snapshot.get("email").toString(),
                    snapshot.get("age").toString().toInt(),
                    snapshot.get("likedSongs") as ArrayList<String>,
                    snapshot.get("subscribedChannels") as ArrayList<String>,
                )
            }
            return user
        }catch (e:Exception){
            return null
        }
    }
}
