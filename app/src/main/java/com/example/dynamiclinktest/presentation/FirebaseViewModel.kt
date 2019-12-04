/*
package com.example.dynamiclinktest.presentation

import android.net.Uri
import android.os.Bundle
import android.util.Log
import com.example.dynamiclinktest.common.BaseViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.dynamiclinks.DynamicLink
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks
import javax.inject.Inject

class FirebaseViewModel @Inject constructor(): BaseViewModel() {

    private var mInvitationUrl: Uri? = null

    fun createLink(): String {
        val user = FirebaseAuth.getInstance().currentUser
        val uid = user!!.uid
        val link = "https://mygame.example.com/?invitedby=$uid"
        val dynamicLink = FirebaseDynamicLinks.getInstance().createDynamicLink()
            .setLink(Uri.parse(link))
            .setDomainUriPrefix("https://example.page.link")
            .setAndroidParameters(
                DynamicLink.AndroidParameters.Builder("com.example.android")
                    .setMinimumVersion(1)
                    .build())
            .setIosParameters(
                DynamicLink.IosParameters.Builder("com.example.ios")
                    .setAppStoreId("123456789")
                    .setMinimumVersion("1.0.1")
                    .build())
            .buildDynamicLink()

        mInvitationUrl = dynamicLink.uri
        Log.d("authent_sign_create", mInvitationUrl.toString())
        return mInvitationUrl.toString()
    }
}*/
