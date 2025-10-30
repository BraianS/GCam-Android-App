package dev.braian.gcamxmlhub.core

import android.app.Application
import android.content.Context
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.PropertyName
import com.google.firebase.database.database
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.braian.gcamxmlhub.data.utils.Constant
import javax.inject.Named
import javax.inject.Singleton

const val WEB_CLIENT_ID = "608299041384-ssk0td9fhpmljkkekutu3iadkehkkh82.apps.googleusercontent.com"

@Module
@InstallIn(SingletonComponent::class)
class FirebaseModule {

    @Provides
    @Singleton
    fun providesFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Provides
    @Singleton
    fun provideFirebaseFirestore(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }

    @Provides
    @Singleton
    fun providesRealtimeDBReference(): DatabaseReference = Firebase.database.getReference()

    @Provides
    fun provideCredentialManager(@ApplicationContext context: Context):CredentialManager {
        return CredentialManager.create(context)
    }

    @Provides
    @Singleton
    fun provideGoogleIdTokenCredentialParser(): GoogleIdTokenCredential.Companion {
        // Configuração para parse das credenciais
        return GoogleIdTokenCredential
    }

    @Provides
    @Named("googleSignInRequest")
    fun provideGoogleSignInRequest(
        app: Application
    ) = GetCredentialRequest.Builder()
        .addCredentialOption(
            GetGoogleIdOption.Builder()
                // Only show accounts previously used to sign in.
                .setFilterByAuthorizedAccounts(true)
                // Your server's client ID, not your Android client ID.
                .setServerClientId(WEB_CLIENT_ID)
                // Automatically sign in when exactly one credential is retrieved.
                .setAutoSelectEnabled(true)
                .build()
        )
        .build()

    @Singleton
    @Provides
    @PropertyName(Constant.LogClass.GOOGLE_SIGN_UP_REQUEST)
    fun provideGoogleSignUpRequest(
    app: Application
    ): GetCredentialRequest {
        val googleIdOption = GetGoogleIdOption.Builder()
            .setServerClientId(WEB_CLIENT_ID)
            .setFilterByAuthorizedAccounts(false)
            .build()

        return GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption)
            .build()

    }


}