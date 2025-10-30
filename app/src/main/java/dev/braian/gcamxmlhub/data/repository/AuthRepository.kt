package dev.braian.gcamxmlhub.data.repository

import android.content.Context
import androidx.credentials.GetCredentialRequest
import dev.braian.gcamxmlhub.core.ApiResult

interface AuthRepository {

    suspend fun login(email: String, password: String): ApiResult<Boolean>
    fun logout(): ApiResult<Boolean>
    suspend fun signInWithEmailAndPassword(
        name: String, email: String, password: String
    ): ApiResult<Boolean>

    fun getCredentialResponse(context: Context, request: GetCredentialRequest)


}