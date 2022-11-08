package com.example.emosic.repository

import com.example.emosic.data.User
import com.example.emosic.utils.Params
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import io.realm.kotlin.mongodb.App
import io.realm.kotlin.mongodb.sync.SyncConfiguration
import io.realm.kotlin.types.ObjectId
import io.realm.kotlin.types.annotations.PrimaryKey

class UserDataRepositoryImpl {
    val app = App.create(Params.APP_ID)
    val user = app.currentUser
    fun getUserData(): User? {
        val config = user?.let {
            SyncConfiguration.Builder(it, setOf(User::class))
                .name("realm name 1")
                .schemaVersion(Params.REALM_DB_VERSION)
                .initialSubscriptions(initialSubscriptionBlock = { realm ->
                    add(
                        realm.query<User>(
                        ),
                        "subscription name"
                    )
                })
                .build()
        }
        val realm = config?.let { Realm.open(it) }
        fun closeRealm(){
            realm?.close()
        }
        val objId = user?.let { ObjectId.from(it.id) }
        val data : User? = realm?.query<User>("_id == $0", objId)?.first()?.find()
        return data
    }
}
