package com.github.scott.gcm.data

import android.content.Context
import io.realm.Realm


class DBUtil() {

    fun insertUser(user: User?) {
        val realm: Realm = Realm.getDefaultInstance()
        realm.beginTransaction()

        // 충돌방지
        val savedUser: User? = realm.where(User::class.java).equalTo("email", user?.email).findFirst()
        if (savedUser == null) {
            realm.insert(user)
        }

        realm.commitTransaction()
    }

    val allUsers: List<User>
        get() {
            val realm: Realm = Realm.getDefaultInstance()
            realm.beginTransaction()
            val list: List<User> = realm.where(User::class.java).findAll()
            val result: List<User> = realm.copyFromRealm(list)
            realm.commitTransaction()
            return result
        }

    fun getallUsers(): List<User>{
        val realm: Realm = Realm.getDefaultInstance()
        realm.beginTransaction()
        val list: List<User> = realm.where(User::class.java).findAll()
        val result: List<User> = realm.copyFromRealm(list)
        realm.commitTransaction()
        return result
    }

    fun getUserById(email: String?): User {
        val realm: Realm = Realm.getDefaultInstance()
        realm.beginTransaction()
        val user: User = realm.where(User::class.java).equalTo("email", email).findFirst()
        val result: User = realm.copyFromRealm(user)
        realm.commitTransaction()
        return result
    }

    fun deleteUserById(email: String?) {
        val realm: Realm = Realm.getDefaultInstance()
        realm.beginTransaction()
        realm.where(User::class.java).equalTo("email", email).findFirst().deleteFromRealm()
        realm.commitTransaction()
    }

    fun deleteAllUsers() {
        val realm: Realm = Realm.getDefaultInstance()
        realm.beginTransaction()
        realm.where(User::class.java).findAll().deleteAllFromRealm()
        realm.commitTransaction()
    }

    fun updateByUserId(email: String?) {
        val realm: Realm = Realm.getDefaultInstance()
        realm.beginTransaction()
        val user: User = realm.where(User::class.java).equalTo("email", email).findFirst()
        user.password = "updatedPwd"
        realm.commitTransaction()
    }
}