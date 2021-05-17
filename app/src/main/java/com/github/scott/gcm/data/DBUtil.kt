package com.github.scott.gcm.data

import com.github.scott.gcm.data.model.*
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

    fun getUserListById(email: String): List<User>? {
        val realm: Realm = Realm.getDefaultInstance()
        realm.beginTransaction()

        val saved = realm.where(User::class.java).contains("email", email).findAll()
        var result: List<User>? = null

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

    fun getCommunityByUser(email: String): Community? {
        val realm: Realm = Realm.getDefaultInstance()
        realm.beginTransaction()
        val list = realm.where(Community::class.java).equalTo("ownerEmail", email).findFirst()

        var result: Community? = null
        if (list != null) {
            result = realm.copyFromRealm(list)
        }

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

    fun getAllReviews(): List<Review> {
        val realm: Realm = Realm.getDefaultInstance()
        realm.beginTransaction()
        val list: List<Review> = realm.where(
            Review::class.java
        ).findAll()
        val result: List<Review> = realm.copyFromRealm(list)
        realm.commitTransaction()
        return result
    }


    // Invitation
    fun insertInvitation(invitation: Invitation) {
        val realm: Realm = Realm.getDefaultInstance()
        realm.beginTransaction()

        // 충돌방지
//        val saved = realm.where(Community::class.java).equalTo("title", community.title).findFirst()
//        if (saved == null) {
//            realm.insert(community)
//        }

        realm.insert(invitation)
        realm.commitTransaction()
    }

    fun getAllInvitation(): List<Invitation> {
        val realm: Realm = Realm.getDefaultInstance()
        realm.beginTransaction()
        val list: List<Invitation> = realm.where(
            Invitation::class.java
        ).findAll()
        val result: List<Invitation> = realm.copyFromRealm(list)
        realm.commitTransaction()
        return result
    }

    // Join Request
    fun insertJoinRequest(joinRequest: JoinRequest) {
        val realm: Realm = Realm.getDefaultInstance()
        realm.beginTransaction()

        // 충돌방지
//        val saved = realm.where(Community::class.java).equalTo("title", community.title).findFirst()
//        if (saved == null) {
//            realm.insert(community)
//        }

        realm.insert(joinRequest)
        realm.commitTransaction()
    }

    fun getAllJoinRequest(community: Community?, loggedinEmail: String): List<JoinRequest> {
        val realm: Realm = Realm.getDefaultInstance()
        realm.beginTransaction()

        var comm: Community? = null


        if (community == null) {
            // mange reuquest에서 호출 시, owneremail을 넘김
            comm = realm.where(
                Community::class.java
            ).equalTo("ownerEmail", loggedinEmail).findFirst()
        } else {
            // detail에서 호출 시, community를 넘김
            comm = community
        }

        var result: List<JoinRequest> = listOf()
        if (comm != null) {
            val list: List<JoinRequest> = realm.where(
                JoinRequest::class.java
            ).equalTo("communityTitle", comm.title).findAll()
            result = realm.copyFromRealm(list)
        }

        realm.commitTransaction()
        return result
    }


}