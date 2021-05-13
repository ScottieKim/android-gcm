package com.github.scott.gcm.data

import com.github.scott.gcm.data.model.Community
import com.github.scott.gcm.data.model.Review
import com.github.scott.gcm.data.model.User
import io.realm.Realm


class DBUtil() {

    fun insertUser(user: User?) {
        val realm: Realm = Realm.getDefaultInstance()
        realm.beginTransaction()

        // 충돌방지
        val savedUser: User? =
            realm.where(User::class.java).equalTo("email", user?.email).findFirst()
        if (savedUser == null) {
            realm.insert(user)
        }

        realm.commitTransaction()
    }

    val allUsers: List<User>
        get() {
            val realm: Realm = Realm.getDefaultInstance()
            realm.beginTransaction()
            val list: List<User> = realm.where(
                User::class.java
            ).findAll()
            val result: List<User> = realm.copyFromRealm(list)
            realm.commitTransaction()
            return result
        }

    fun getallUsers(): List<User> {
        val realm: Realm = Realm.getDefaultInstance()
        realm.beginTransaction()
        val list: List<User> = realm.where(
            User::class.java
        ).findAll()
        val result: List<User> = realm.copyFromRealm(list)
        realm.commitTransaction()
        return result
    }

    fun getUserById(email: String): User? {
        val realm: Realm = Realm.getDefaultInstance()
        realm.beginTransaction()

        val saved = realm.where(User::class.java).equalTo("email", email).findFirst()
        var result: User? = null

        if (saved != null) {
            result = realm.copyFromRealm(saved)
        }

        realm.commitTransaction()
        return result
    }

    fun deleteUserById(email: String) {
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
        val user: User = realm.where(
            User::class.java
        ).equalTo("email", email).findFirst()
        user.password = "updatedPwd"
        realm.commitTransaction()
    }

    // Community
    fun insertCommunity(community: Community) {
        val realm: Realm = Realm.getDefaultInstance()
        realm.beginTransaction()

        // 충돌방지
        val saved =
            realm.where(Community::class.java).equalTo("title", community.title).findFirst()
        if (saved == null) {
            realm.insert(community)
        }

        realm.commitTransaction()
    }

    fun getAllCommunity(): List<Community> {
        val realm: Realm = Realm.getDefaultInstance()
        realm.beginTransaction()
        val list = realm.where(Community::class.java).findAll()
        val result = realm.copyFromRealm(list)
        realm.commitTransaction()
        return result
    }

    fun getCommunityByTitle(title: String): Community {
        val realm: Realm = Realm.getDefaultInstance()
        realm.beginTransaction()
        val list = realm.where(Community::class.java).equalTo("title", title).findFirst()
        val result = realm.copyFromRealm(list)
        realm.commitTransaction()
        return result
    }

    // Review
    fun insertReview(review: Review) {
        val realm: Realm = Realm.getDefaultInstance()
        realm.beginTransaction()

        // 충돌방지
//        val saved = realm.where(Community::class.java).equalTo("title", community.title).findFirst()
//        if (saved == null) {
//            realm.insert(community)
//        }

        realm.insert(review)
        realm.commitTransaction()
    }
}