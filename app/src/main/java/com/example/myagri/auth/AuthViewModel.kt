package com.example.myagri.auth

import android.content.Context
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.android.gms.auth.api.signin.GoogleSignInAccount

class AuthViewModel : ViewModel() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

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

    fun logout(
        context: Context,
        onLogout: () -> Unit
    ) {
        // Firebase logout
        auth.signOut()

        // Google logout
        val googleSignInClient = GoogleAuthHelper.getClient(context)
        googleSignInClient.signOut().addOnCompleteListener {
            onLogout()
        }
    }
}