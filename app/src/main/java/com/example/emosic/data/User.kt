package com.example.emosic.data

import io.realm.kotlin.types.ObjectId
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

class User: RealmObject {
    @PrimaryKey
    var _id : ObjectId ?= null
    var name: String = ""
    var email : String = ""
    var age : Int = 0
}