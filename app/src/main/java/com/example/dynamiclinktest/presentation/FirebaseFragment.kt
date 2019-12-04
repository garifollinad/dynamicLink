//package com.example.dynamiclinktest.presentation
//
//
//import android.app.Activity
//import android.content.Intent
//import android.net.Uri
//import android.os.Bundle
//import android.util.Log
//import androidx.fragment.app.Fragment
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.TextView
//import android.widget.Toast
//import androidx.core.app.ActivityCompat
//import androidx.lifecycle.ViewModelProvider
//import androidx.lifecycle.ViewModelProviders
//
//import com.example.dynamiclinktest.R
//import com.example.dynamiclinktest.di.Injectable
//import com.google.android.gms.auth.api.signin.GoogleSignIn
//import com.google.android.gms.common.api.ApiException
//import com.google.firebase.auth.FirebaseAuth
//import com.google.firebase.database.FirebaseDatabase
//import com.google.firebase.dynamiclinks.FirebaseDynamicLinks
//import javax.inject.Inject
//
//class FirebaseFragment : Fragment(), Injectable {
//
//    private lateinit var linker: TextView
//    private lateinit var auth: FirebaseAuth
//
//    @Inject
//    lateinit var viewModelFactory: ViewModelProvider.Factory
//
//    private val viewModel by lazy {
//        ViewModelProviders.of(activity!!, viewModelFactory).get(FirebaseViewModel::class.java)
//    }
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_firebase, container, false)
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        bindViews(view)
//        setData()
//    }
//
//
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        Log.d("GoogleActivity_user", "jjjj")
//
//        if (requestCode == 9001) {
//            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
//            try {
//                val account = task.getResult(ApiException::class.java)
//            } catch (e: ApiException) {
//            }
//        }
//    }
//
//    private fun bindViews(view: View) = with(view) {
//        linker = findViewById(R.id.linker)
//    }
//
//    private fun setData() {
//        linker.text = viewModel.createLink()
//        auth = FirebaseAuth.getInstance()
//        signIn(auth, "lollola2019@gmail.com", "Iloveadilbek1")
//        linker.setOnClickListener {
//            sendInvitation(auth)
//        }
//    }
//
//    fun sendInvitation(auth: FirebaseAuth) {
//        val referrerName = auth.currentUser?.email
//        val subject = String.format("%s wants you to play MyExampleGame!", referrerName)
//        val invitationLink = "https://mytester.page.link"
//        val msg = "Let's play MyExampleGame together! Use my referrer link: $invitationLink"
//
//        val intent = Intent(Intent.ACTION_SENDTO)
//        intent.data = Uri.parse("mailto:") // only email apps should handle this
//        intent.putExtra(Intent.EXTRA_SUBJECT, subject)
//        intent.putExtra(Intent.EXTRA_TEXT, msg)
//        if (intent.resolveActivity(activity!!.packageManager) != null) {
//            startActivityForResult(intent, 1)
//        }
//
//    }
//
//
//    fun createAccount(auth: FirebaseAuth, email: String, password: String) {
//        Log.d("mkk", "createAccount:$email")
//        auth.createUserWithEmailAndPassword(email, password)
//            .addOnCompleteListener(activity as Activity) { task ->
//                if (task.isSuccessful) {
//                    val user = auth.currentUser
//                    Log.d("authent_test", user.toString())
//                } else {
//                    Log.w("authent_test_fail", "createUserWithEmail:failure", task.exception)
//                }
//            }
//    }
//
//    fun signIn(auth: FirebaseAuth, email: String, password: String) {
//        auth.signInWithEmailAndPassword(email, password)
//            .addOnCompleteListener(activity as Activity) { task ->
//                if (task.isSuccessful) {
//                    val user = auth.currentUser
//                    Log.d("authent_sign", user?.email.toString())
//
//                } else {
//                    Log.w("authent_sign_test", "signInWithEmail:failure", task.exception)
//                }
//            }
//    }
//
//
//    private fun createAnonymousAccountWithReferrerInfo(referrerUid: String?) {
//        FirebaseAuth.getInstance()
//            .signInAnonymously()
//            .addOnSuccessListener {
//                val user = FirebaseAuth.getInstance().currentUser
//                val userRecord = FirebaseDatabase.getInstance().reference
//                    .child("users")
//                    .child(user!!.uid)
//                userRecord.child("referred_by").setValue(referrerUid)
//            }
//    }
//
//}
