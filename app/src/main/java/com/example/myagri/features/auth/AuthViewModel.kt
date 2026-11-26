package com.example.myagri.features.auth

import android.content.Context
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.android.gms.auth.api.signin.GoogleSignInAccount

import android.app.Activity
import com.example.myagri.data.remote.GoogleAuthHelper
import com.google.firebase.FirebaseException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit


class AuthViewModel : ViewModel() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    // For phone auth
    var verificationId: String? = null

    // GOOGLE AUTH
    fun firebaseAuthWithGoogle(
        account: GoogleSignInAccount,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)

        auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onSuccess()
                } else {
                    onFailure(task.exception?.message ?: "Login Failed")
                }
            }
    }

    // PHONE AUTH (SEND OTP)
    fun sendOtp(
        phone: String,
        activity: Activity,
        onCodeSent: () -> Unit,
        onError: (String) -> Unit
    ) {
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phone) // MUST be +91XXXXXXXXXX
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(activity)
            .setCallbacks(object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                    // Auto verification
                    signInWithCredential(credential, {}, onError)
                }

                override fun onVerificationFailed(e: FirebaseException) {
                    onError(e.message ?: "Verification Failed")
                }

                override fun onCodeSent(
                    verId: String,
                    token: PhoneAuthProvider.ForceResendingToken
                ) {
                    verificationId = verId
                    onCodeSent()
                }
            })
            .build()

        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    //  VERIFY OTP
    fun verifyOtp(
        otp: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        val verId = verificationId
        if (verId == null) {
            onError("Verification ID not found")
            return
        }

        val credential = PhoneAuthProvider.getCredential(verId, otp)
        signInWithCredential(credential, onSuccess, onError)
    }

    //  COMMON SIGN-IN
    private fun signInWithCredential(
        credential: PhoneAuthCredential,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    onSuccess()
                } else {
                    onError(it.exception?.message ?: "Login Failed")
                }
            }
    }

    // ---------------- LOGOUT ----------------
    fun logout(
        context: Context,
        onLogout: () -> Unit
    ) {
        auth.signOut()

        val googleSignInClient = GoogleAuthHelper.getClient(context)
        googleSignInClient.signOut().addOnCompleteListener {
            onLogout()
        }
    }
}