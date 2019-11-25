package com.example.dynamiclinktest

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks
import androidx.annotation.NonNull
import androidx.fragment.app.FragmentActivity
import com.google.android.gms.appinvite.AppInviteReferral
import com.google.android.gms.tasks.OnFailureListener
import com.google.firebase.dynamiclinks.PendingDynamicLinkData
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.appinvite.FirebaseAppInvite
import com.google.firebase.dynamiclinks.DynamicLink
import com.google.firebase.dynamiclinks.ShortDynamicLink
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth


class MainActivity : AppCompatActivity() {
    private lateinit var linker: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        createDynamicLinkReward()
    }

    fun createDynamicLink_Basic() {
        // [START create_link_basic]
        val dynamicLink = FirebaseDynamicLinks.getInstance().createDynamicLink()
            .setLink(Uri.parse("https://www.example.com/"))
            .setDomainUriPrefix("https://example.page.link")
            // Open links with this app on Android
            .setAndroidParameters(DynamicLink.AndroidParameters.Builder().build())
            // Open links with com.example.ios on iOS
            .setIosParameters(DynamicLink.IosParameters.Builder("com.example.ios").build())
            .buildDynamicLink()

        val dynamicLinkUri = dynamicLink.uri

        linker = findViewById(R.id.linker)
        linker.text = dynamicLinkUri.toString()
        linker.setOnClickListener {
            shareLink(dynamicLinkUri)
        }
        // [END create_link_basic]
    }

    fun createDynamicLinkReward() {

        val user = FirebaseAuth.getInstance().currentUser
        val uid = user!!.uid
        val link = "https://metester.page.link/?invitedby=$uid"
        FirebaseDynamicLinks.getInstance().createDynamicLink()
            .setLink(Uri.parse(link))
            .setDomainUriPrefix("https://example.page.link")
            .setAndroidParameters(
                DynamicLink.AndroidParameters.Builder("com.example.android")
                    .setMinimumVersion(125)
                    .build())
            .setIosParameters(
                DynamicLink.IosParameters.Builder("com.example.ios")
                    .setAppStoreId("123456789")
                    .setMinimumVersion("1.0.1")
                    .build())
            .buildShortDynamicLink()
            .addOnSuccessListener { shortDynamicLink ->
                val mInvitationUrl = shortDynamicLink.shortLink.toString()
                linker = findViewById(R.id.linker)
                linker.text = mInvitationUrl.toString()


                val referrerName = FirebaseAuth.getInstance().currentUser?.displayName
                val subject = String.format("%s wants you to play MyExampleGame!", referrerName)
                val invitationLink = mInvitationUrl.toString()
                val msg = "Let's play MyExampleGame together! Use my referrer link: $invitationLink"
                val msgHtml = String.format("<p>Let's play MyExampleGame together! Use my " +
                        "<a href=\"%s\">referrer link</a>!</p>", invitationLink)

                val intent = Intent(Intent.ACTION_SENDTO)
                intent.data = Uri.parse("mailto:") // only email apps should handle this
                intent.putExtra(Intent.EXTRA_SUBJECT, subject)
                intent.putExtra(Intent.EXTRA_TEXT, msg)
                intent.putExtra(Intent.EXTRA_HTML_TEXT, msgHtml)
                if (intent.resolveActivity(packageManager) != null) {
                    startActivity(intent)
                }
//                linker.setOnClickListener {
//                    shareLink(shortDynamicLink.shortLink)
//                }
            }

    }

    fun shareLink(myDynamicLink: Uri) {
        // [START ddl_share_link]
        val sendIntent = Intent()
        val msg = "Click a link: $myDynamicLink"
        sendIntent.action = Intent.ACTION_SEND
        sendIntent.putExtra(Intent.EXTRA_TEXT, msg)
        sendIntent.type = "text/plain"
        startActivity(sendIntent)
        // [END ddl_share_link]
    }
}
