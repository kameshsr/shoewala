package com.shoewala.app.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.shoewala.app.utils.FirebaseUtil

object UserRepository {

    private val db = FirebaseFirestore.getInstance()

    fun saveUser() {
        val user = FirebaseUtil.getAuth().currentUser ?: return

        val userMap = hashMapOf(
            "uid" to user.uid,
            "email" to user.email,
            "createdAt" to System.currentTimeMillis()
        )

        db.collection("users")
            .document(user.uid)
            .set(userMap)
    }
}
