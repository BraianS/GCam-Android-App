package dev.braian.gcamxmlhub.data.repository.impl

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import androidx.credentials.exceptions.GetCredentialCancellationException
import androidx.credentials.exceptions.GetCredentialCustomException
import androidx.credentials.exceptions.GetCredentialException
import androidx.credentials.exceptions.NoCredentialException
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.database.DatabaseReference
import com.google.firebase.firestore.FirebaseFirestore
import dev.braian.gcamxmlhub.core.ApiResult
import dev.braian.gcamxmlhub.core.WEB_CLIENT_ID
import dev.braian.gcamxmlhub.data.model.Subscription
import dev.braian.gcamxmlhub.data.repository.AuthRepository
import dev.braian.gcamxmlhub.data.utils.Constant
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.tasks.await
import java.security.MessageDigest
import java.util.UUID
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton


@Singleton
class LoginService @Inject constructor(
    private val firebaseStore: FirebaseFirestore,
    private val firebaseAuth: FirebaseAuth,
    private val databaseReference: DatabaseReference,
    private var credentialManager: CredentialManager,
    @Named("googleSignInRequest")
    private var googleIdSignInRequest: GetCredentialRequest,
    /*@PropertyName(Constant.LogClass.GOOGLE_SIGN_UP_REQUEST)
    private val googleSignUpRequest: GetCredentialRequest*/

) : AuthRepository {


    private val _currentUser = MutableStateFlow<FirebaseUser?>(null)
    val currentUSer: StateFlow<FirebaseUser?> = _currentUser



    init {
        firebaseAuth.addAuthStateListener {
            _currentUser.value = it.currentUser
        }
    }

    suspend fun getGoogleCredentialResponse(context: Context): GetCredentialResponse {
        return credentialManager.getCredential(context, googleIdSignInRequest)
    }

    // This method handles the Firebase authentication.
    suspend fun signInWithGoogleToken(idToken: String): ApiResult<Boolean> {
        return try {
            val credential = GoogleAuthProvider.getCredential(idToken, null)
            val authResult = firebaseAuth.signInWithCredential(credential).await()
            ApiResult.Success(authResult.user != null)
        } catch (e: Exception) {
            ApiResult.Error(e)
        }
    }


    override suspend fun login(
        email: String,
        password: String
    ): ApiResult<Boolean>{
        return try {
            val result = firebaseAuth.signInWithEmailAndPassword(email, password).result

            ApiResult.Success(result != null)
        } catch (e: Exception) {
            ApiResult.Error(e)
        }
    }

    override fun logout():ApiResult<Boolean> {
        return try {
            Log.d(Constant.LogClass.LOGIN_SERVICE, "Logout")
            firebaseAuth.signOut()
            ApiResult.Success(true)
        } catch (e:Exception) {
            Log.e(Constant.LogClass.LOGIN_SERVICE, "Logout error: ${e.message}")
            ApiResult.Error(e)
        }
    }



    override suspend fun signInWithEmailAndPassword(
        name: String,
        email: String,
        password: String
    ): ApiResult<Boolean> {
        return try {
            // 1. Create the user in Firebase Auth and wait for it to complete.
            val authResult = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            val firebaseUser = authResult.user

            if (firebaseUser != null) {
                // 2. Prepare the user data to be saved.
                val user = mapOf(
                    "id" to firebaseUser.uid,
                    "name" to name,
                    "email" to email,
                    "subscription" to Subscription.Basic
                )

                // 3. Save the user data to Firestore and wait for it to complete.
                databaseReference.child("users")
                    .child(firebaseUser.uid) // Use .document() for a specific ID
                    .setValue(user)
                    .await() // This will suspend until the operation is finished.

                // 4. If both steps succeed, return success.
                ApiResult.Success(true)
            } else {
                // Handle the rare case where user creation fails without an exception.
                ApiResult.Error(Exception("User creation failed: FirebaseUser is null"))
            }
        } catch (e: Exception) {
            // This will catch exceptions from both auth and Firestore operations.
            ApiResult.Error(e)
        }
    }

    override fun getCredentialResponse(
        context: Context,
        request: GetCredentialRequest
    ) {

    }

    suspend fun signWithGoogle(context: Context):ApiResult<Boolean> {


        val googleIdOption: GetGoogleIdOption = GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(true)
            .setServerClientId(WEB_CLIENT_ID)
            .setAutoSelectEnabled(true)
            // nonce string to use when generating a Google ID token
            .setNonce(Nonce())
            .build()

        Log.d(Constant.LogClass.LOGIN_SERVICE, "Google Options " +googleIdOption.toString())


        val request = GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption)
            .build()

        Log.d(Constant.LogClass.LOGIN_SERVICE, "Google Request " +request.toString())

        val e = signIn(request, context)
        // If the sign-in fails with NoCredentialException,  there are no authorized accounts.
        // In this case, we attempt to sign in again with filtering disabled.
        if (e is NoCredentialException) {
            val googleIdOptionFalse: GetGoogleIdOption = GetGoogleIdOption.Builder()
                .setFilterByAuthorizedAccounts(false)
                .setServerClientId(WEB_CLIENT_ID)
                .setNonce(Nonce())
                .build()

            val requestFalse: GetCredentialRequest = GetCredentialRequest.Builder()
                .addCredentialOption(googleIdOptionFalse)
                .build()

            //We will build out this function in a moment
            signIn(requestFalse, context)
        }
        return ApiResult.Success(true)
    }

    suspend fun signIn(request: GetCredentialRequest, context: Context): Exception? {
        val credentialManager = CredentialManager.create(context)
        val failureMessage = "Sign in failed!"
        var e: Exception? = null
        //using delay() here helps prevent NoCredentialException when the BottomSheet Flow is triggered
        //on the initial running of our app
        delay(250)
        try {
            // The getCredential is called to request a credential from Credential Manager.
            val result = credentialManager.getCredential(
                request = request,
                context = context,
            )
            Log.i(Constant.LogClass.LOGIN_SERVICE, result.toString())

            Toast.makeText(context, "Sign in successful!", Toast.LENGTH_SHORT).show()
            Log.i(Constant.LogClass.LOGIN_SERVICE, "(☞ﾟヮﾟ)☞  Sign in Successful!  ☜(ﾟヮﾟ☜)")

        } catch (e: GetCredentialException) {
            Toast.makeText(context, failureMessage, Toast.LENGTH_SHORT).show()
            Log.e(Constant.LogClass.LOGIN_SERVICE, failureMessage + ": Failure getting credentials", e)

        } catch (e: GoogleIdTokenParsingException) {
            Toast.makeText(context, failureMessage, Toast.LENGTH_SHORT).show()
            Log.e(Constant.LogClass.LOGIN_SERVICE, failureMessage + ": Issue with parsing received GoogleIdToken", e)

        } catch (e: NoCredentialException) {
            Toast.makeText(context, failureMessage, Toast.LENGTH_SHORT).show()
            Log.e(Constant.LogClass.LOGIN_SERVICE, failureMessage + ": No credentials found", e)
            return e

        } catch (e: GetCredentialCustomException) {
            Toast.makeText(context, failureMessage, Toast.LENGTH_SHORT).show()
            Log.e(Constant.LogClass.LOGIN_SERVICE, failureMessage + ": Issue with custom credential request", e)

        } catch (e: GetCredentialCancellationException) {
            Toast.makeText(context, ": Sign-in cancelled", Toast.LENGTH_SHORT).show()
            Log.e(Constant.LogClass.LOGIN_SERVICE, failureMessage + ": Sign-in was cancelled", e)
        }
        return e
    }

    fun handleSignIn(result: GetCredentialResponse) {

        val credential = result.credential as CustomCredential
        val responseJson: String

        when(credential){
            is CustomCredential -> {
                if (credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                    try {
                        // Use googleIdTokenCredential and extract the ID to validate and
                        // authenticate on your server.
                        val googleIdTokenCredential = GoogleIdTokenCredential
                            .createFrom(credential.data)
                        // You can use the members of googleIdTokenCredential directly for UX
                        // purposes, but don't use them to store or control access to user
                        // data. For that you first need to validate the token:
                        // pass googleIdTokenCredential.getIdToken() to the backend server.
                        // see [validation instructions](https://developers.google.com/identity/gsi/web/guides/verify-google-id-token)
                    } catch (e: GoogleIdTokenParsingException) {
                        Log.e(Constant.LogClass.LOGIN_VIEWMODEL, "Received an invalid google id token response", e)
                    }
                } else {
                    // Catch any unrecognized custom credential type here.
                    Log.e(Constant.LogClass.LOGIN_VIEWMODEL, "Unexpected type of credential")
                }
            }

            else -> {
                // Catch any unrecognized credential type here.
                Log.e(Constant.LogClass.LOGIN_VIEWMODEL, "Unexpected type of credential")
            }
        }


    }



    fun Nonce(): String {
        val rawNonce = UUID.randomUUID().toString()
        val bytes = rawNonce.toByteArray(Charsets.UTF_8)
        val md = MessageDigest.getInstance("SHA-256")
        val digest = md.digest(bytes)
        return digest.fold("") { str, it -> str + "%02x".format(it) }
    }

}

sealed class ValidationEvent {
    object Success:ValidationEvent()
}

