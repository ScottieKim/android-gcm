package com.github.scott.gcm.data

import com.github.scott.gcm.data.model.*
import com.github.scott.gcm.data.model.Community.Companion.FIELD_COMMUNITY_TITLE
import com.github.scott.gcm.data.model.Invitation.Companion.FIELD_INVITATION_COMMUNITY_TITLE
import com.github.scott.gcm.data.model.Invitation.Companion.FIELD_INVITATION_GUEST_EMAIL
import com.github.scott.gcm.data.model.Invitation.Companion.FIELD_INVITATION_HOST_EMAIL
import com.github.scott.gcm.data.model.JoinRequest.Companion.FIELD_JOIN_REQUEST_COMMUNITY_TITLE
import com.github.scott.gcm.data.model.JoinRequest.Companion.FIELD_JOIN_REQUEST_GUEST_EMAIL
import com.github.scott.gcm.data.model.Review.Companion.FIELD_REVIEW_COMMUNITY_TITLE
import com.github.scott.gcm.data.model.Review.Companion.FIELD_REVIEW_EMAIL
import com.github.scott.gcm.data.model.User.Companion.FIELD_USER_EMAIL
import io.realm.Realm
import io.realm.RealmObject


class DBUtil {

    // User
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

    fun getCommunityByType(type: String): List<Community> {
        val realm: Realm = Realm.getDefaultInstance()
        realm.beginTransaction()
        val list = realm.where(Community::class.java).equalTo("type", type).findAll()

        var result: List<Community> = listOf()
        if (list != null) {
            result = realm.copyFromRealm(list)
        }

        realm.commitTransaction()
        return result
    }

    // CommunityUser
    fun getCommunityUserByUser(email: String): List<CommunityUser> {
        val realm: Realm = Realm.getDefaultInstance()
        realm.beginTransaction()
        val list = realm.where(CommunityUser::class.java)
            .equalTo(CommunityUser.FIELD_COMMUNITY_USER_EMAIL, email).findAll()

        var result: List<CommunityUser> = listOf()
        if (list != null) {
            result = realm.copyFromRealm(list)
        }

        realm.commitTransaction()
        return result
    }

    fun getCommunityUserByUserAndTitle(email: String, title: String): CommunityUser? {
        val realm: Realm = Realm.getDefaultInstance()
        realm.beginTransaction()
        val item = realm.where(CommunityUser::class.java)
            .equalTo(CommunityUser.FIELD_COMMUNITY_USER_EMAIL, email)
            .equalTo(CommunityUser.FIELD_COMMUNITY_TITLE, title).findFirst()

        var result: CommunityUser? = null
        if (item != null) {
            result = realm.copyFromRealm(item)
        }

        realm.commitTransaction()
        return result
    }

    // Join Request
    fun getAllJoinRequest(community: Community?, loggedinEmail: String): List<JoinRequest> {
        val realm: Realm = Realm.getDefaultInstance()
        realm.beginTransaction()

        var comm: Community? = null


        if (community == null) {
            // mange reuquest에서 호출 시, owneremail을 넘김
            comm =
                realm.where(Community::class.java).equalTo("ownerEmail", loggedinEmail).findFirst()
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

    fun getJoinRequestByUser(email: String, communityTitle: String): JoinRequest? {
        val realm: Realm = Realm.getDefaultInstance()
        realm.beginTransaction()
        val list =
            realm.where(JoinRequest::class.java)
                .equalTo(FIELD_JOIN_REQUEST_GUEST_EMAIL, email)
                .equalTo(FIELD_JOIN_REQUEST_COMMUNITY_TITLE, communityTitle)
                .findFirst()

        var result: JoinRequest? = null
        if (list != null) {
            result = realm.copyFromRealm(list)
        }

        realm.commitTransaction()
        return result
    }

    // Invitation
    fun getAllInvitationByUser(guestEmail: String): List<Invitation> {
        val realm: Realm = Realm.getDefaultInstance()
        realm.beginTransaction()
        val list =
            realm.where(Invitation::class.java).equalTo(FIELD_INVITATION_GUEST_EMAIL, guestEmail)
                .findAll()
        val result = realm.copyFromRealm(list)
        realm.commitTransaction()
        return result
    }

    fun getInvitationByUser(guestEmail: String, communityTitle: String): Invitation {
        val realm: Realm = Realm.getDefaultInstance()
        realm.beginTransaction()
        val list =
            realm.where(Invitation::class.java).equalTo(FIELD_INVITATION_GUEST_EMAIL, guestEmail)
                .equalTo(FIELD_INVITATION_COMMUNITY_TITLE, communityTitle).findFirst()
        val result = realm.copyFromRealm(list)
        realm.commitTransaction()
        return result
    }

    // Review
    fun getReviewByUserAndTitle(email: String, title: String): Review? {
        val realm: Realm = Realm.getDefaultInstance()
        realm.beginTransaction()
        val item =
            realm.where(Review::class.java).equalTo(FIELD_REVIEW_EMAIL, email)
                .equalTo(FIELD_REVIEW_COMMUNITY_TITLE, title).findFirst()
        var result: Review? = null
        if (item != null) {
            result = realm.copyFromRealm(item)
        }

        realm.commitTransaction()
        return result
    }

    inline fun <reified T : RealmObject> insertEntity(entity: T) {
        val realm: Realm = Realm.getDefaultInstance()
        realm.beginTransaction()

        // 충돌방지
        val query = realm.where(T::class.java)
        var saved: T? = null

        when (entity) {
            is Community -> {
                saved =
                    query.equalTo(FIELD_COMMUNITY_TITLE, (entity as Community).title).findFirst()
            }
            is User -> {
                saved = query.equalTo(FIELD_USER_EMAIL, (entity as User).email).findFirst()
            }
            is Review -> {
                saved = query.equalTo(FIELD_REVIEW_EMAIL, (entity as Review).email)
                    .equalTo(FIELD_REVIEW_COMMUNITY_TITLE, entity.communityTitle).findFirst()
            }
            is Invitation -> {
                saved = query.equalTo(FIELD_INVITATION_HOST_EMAIL, (entity as Invitation).hostEmail)
                    .equalTo(FIELD_INVITATION_GUEST_EMAIL, (entity as Invitation).guestEmail)
                    .equalTo(
                        FIELD_INVITATION_COMMUNITY_TITLE,
                        (entity as Invitation).communityTitle
                    )
                    .findFirst()
            }
            is JoinRequest -> {
                saved = query.equalTo(
                    FIELD_JOIN_REQUEST_GUEST_EMAIL,
                    (entity as JoinRequest).guestEmail
                ).equalTo(
                    FIELD_JOIN_REQUEST_COMMUNITY_TITLE,
                    (entity as JoinRequest).communityTitle
                )
                    .findFirst()
            }
        }

        if (saved == null) {
            realm.insert(entity)
        }

        realm.commitTransaction()
    }

    inline fun <reified T : RealmObject> getAllEntities(): List<T> {
        val realm = Realm.getDefaultInstance()
        realm.beginTransaction()

        val list = realm.where(T::class.java).findAll()
        val result = realm.copyFromRealm(list)
        realm.commitTransaction()
        return result
    }

    inline fun <reified T : RealmObject> deleteEntity(entity: T) {
        val realm = Realm.getDefaultInstance()
        realm.beginTransaction()
        val query = realm.where(T::class.java)
        var saved: T? = null
        when (entity) {
            is JoinRequest -> {
                saved = query
                    .equalTo(FIELD_JOIN_REQUEST_COMMUNITY_TITLE, entity.communityTitle)
                    .equalTo(FIELD_JOIN_REQUEST_GUEST_EMAIL, entity.guestEmail).findFirst()
            }
            is Invitation -> {
                saved = query
                    .equalTo(FIELD_INVITATION_COMMUNITY_TITLE, entity.communityTitle)
                    .equalTo(FIELD_INVITATION_GUEST_EMAIL, entity.guestEmail).findFirst()
            }
        }
        saved?.deleteFromRealm()
        realm.commitTransaction()
    }

}