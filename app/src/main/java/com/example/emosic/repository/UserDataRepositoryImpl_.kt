package com.example.emosic.repository

import com.example.emosic.data.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

object UserDataRepositoryImpl_{
    var user : User? = null
    suspend fun getUserData() {
        try {
            val db = FirebaseFirestore.getInstance()
            val snapshot = db.collection("User")
                .document(FirebaseAuth.getInstance().currentUser?.uid.toString())
                .get()
                .await()
            var user: User? = null
            if(snapshot.exists()){
                user = snapshot.toObject(User::class.java)
//                user = User(
//                    snapshot.get("name").toString(),
//                    snapshot.get("email").toString(),
//                    snapshot.get("age").toString().toInt(),
//                    snapshot.get("likedSongs") as ArrayList<String>,
//                    snapshot.get("subscribedChannels") as ArrayList<String>,
//                )
            }
            this.user = user
        }catch (_:Exception){
            user = null
        }
    }
}