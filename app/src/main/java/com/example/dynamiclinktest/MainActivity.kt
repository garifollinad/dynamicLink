package com.example.dynamiclinktest

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks
import com.google.firebase.dynamiclinks.DynamicLink
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {
    private lateinit var linker: TextView
    private var mInvitationUrl: Uri? = null
    private lateinit var auth: FirebaseAuth

    fun createLink() {
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
        linker = findViewById(R.id.linker)
        linker.text = mInvitationUrl.toString()
        Log.d("authent_sign_create", mInvitationUrl.toString())
        linker.setOnClickListener {
            sendInvitation()
        }
    }

    fun sendInvitation() {
        val referrerName = auth.currentUser?.email
        val subject = String.format("%s wants you to play MyExampleGame!", referrerName)
        val invitationLink = "https://mytester.page.link"
        val msg = "Let's play MyExampleGame together! Use my referrer link: $invitationLink"
        val msgHtml = String.format("<p>Let's play MyExampleGame together! Use my " +
                "<a href=\"%s\">referrer link</a>!</p>",  "https://mytester.page.link")

        val intent = Intent(Intent.ACTION_SENDTO)
        intent.data = Uri.parse("mailto:") // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, subject)
        intent.putExtra(Intent.EXTRA_TEXT, msg)
        intent.putExtra(Intent.EXTRA_HTML_TEXT, msgHtml)
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        }

    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.d("GoogleActivity_user", "jjjj")

        if (requestCode == 9001) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
            } catch (e: ApiException) {
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        auth = FirebaseAuth.getInstance()
        signIn("lollola2019@gmail.com", "Iloveadilbek1")
        //createAccount("lollola2020@gmail.com", "Iloveadilbek1")
        createLink()

        FirebaseDynamicLinks.getInstance()
            .getDynamicLink(intent)
            .addOnSuccessListener(this) { pendingDynamicLinkData ->
                var deepLink: Uri? = null
                if (pendingDynamicLinkData != null) {
                    deepLink = pendingDynamicLinkData.link
                }

                val user = FirebaseAuth.getInstance().currentUser
                if (user == null &&
                    deepLink != null &&
                    deepLink.getBooleanQueryParameter("invitedby", false)) {
                    val referrerUid = deepLink.getQueryParameter("invitedby")
                    createAnonymousAccountWithReferrerInfo(referrerUid)
                }
            }
    }

    private fun createAnonymousAccountWithReferrerInfo(referrerUid: String?) {
        FirebaseAuth.getInstance()
            .signInAnonymously()
            .addOnSuccessListener {
                val user = FirebaseAuth.getInstance().currentUser
                val userRecord = FirebaseDatabase.getInstance().reference
                    .child("users")
                    .child(user!!.uid)
                userRecord.child("referred_by").setValue(referrerUid)
            }
    }

    private fun createAccount(email: String, password: String) {
        Log.d("mkk", "createAccount:$email")

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    Log.d("authent_test", user.toString())
                } else {
                    Log.w("authent_test_fail", "createUserWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun signIn(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    Log.d("authent_sign", user?.email.toString())

                } else {
                    Log.w("authent_sign_test", "signInWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                }
            }
    }

}
