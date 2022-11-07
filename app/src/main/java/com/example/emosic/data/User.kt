package com.example.emosic.data

import io.realm.kotlin.types.ObjectId
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject
import io.realm.*
import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.ext.realmSetOf
import io.realm.kotlin.types.RealmSet
import io.realm.kotlin.types.annotations.PrimaryKey

class User: RealmObject {
    @PrimaryKey
    var _id : ObjectId ?= null
    var name: String = ""
    var email : String = ""
    var age : Int = 0
    var likedSongs : RealmSet<String> = realmSetOf()
    var subscribedChannels : RealmSet<String> = realmSetOf()
}
